import {useEffect, useState} from "react";
import ClassCard from "./ClassCard";

function ClassesView() {
    const userFromLocalStorage = JSON.parse(localStorage.getItem("USER")) ?? null;
    const userId = userFromLocalStorage ? userFromLocalStorage.id : null;
    const [courses, setCourses] = useState([]);
    const [showClasses, setShowClasses] = useState(false);
    const [classes, setClasses] = useState([]);
    const [currentCourseName, setCurrentCourseName] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            if (userId == null)
                return
            try {
                const response = await fetch(`http://localhost:8080/courses/user/${userId}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                if (response.ok) {
                    const data = await response.json();
                    setCourses(data);
                    console.log("Courses: ", data);
                } else {
                    console.log("Wrong user id!");
                }
            } catch (error) {
                console.log("Error while loading classes: ", error);
            }
        };

        // const fetchSoftwares = () => {
        //     try {
        //         const response = await fetch("http")
        //     }
        // }

        fetchData();
    }, [userId]);

    async function showClassesWithinCourse(courseId, courseName) {
        try {
            const response = await fetch(`http://localhost:8080/classes/course_id/${courseId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                const data = await response.json();
                setClasses(data);
                console.log("Classes: ", data);
                setShowClasses(true);
                setCurrentCourseName(courseName);
            } else {
                console.log("Wrong course id!");
                setShowClasses(false);
            }
        } catch (error) {
            console.log("Error while loading course's classes: ", error);
        }
    }

    return (
        <div className="p-4">
            <h1>Moje kursy</h1>
            <hr></hr>
            <div className="d-flex mb-5">
                {courses.length > 0 ? (
                    courses.map(course =>
                        <div className="col-6 d-flex me-3 px-3 py-2 rounded-2 bg-light">
                            <h4 className="me-5">{course.name}</h4>
                            <p className="me-5">Starosta grupy: {course.studentRep.firstName} {course.studentRep.lastName}</p>
                            <button className="btn btn-secondary" onClick={() => showClassesWithinCourse(course.id, course.name)}>Szczegóły kursu</button>
                        </div>
                    )
                ) : (
                    <div className="p-3">
                        <p>Nie prowadzisz żadnych kursów</p>
                    </div>
                )}
            </div>
            {showClasses &&
                <div>
                    <h2>Zajęcia w ramach przedmiotu <i>{currentCourseName}</i>:</h2>
                    <hr></hr>
                    <div className="d-flex">
                        {classes.length > 0 ? (
                            classes.map(classSession =>
                                <ClassCard classSession={classSession}/>
                            )
                        ) : (
                            <div className="p-3">
                                <p>Nie masz żadnych zajęć w tym kursie</p>
                            </div>
                        )}
                    </div>
                </div>}
        </div>
    )
}

export default ClassesView;