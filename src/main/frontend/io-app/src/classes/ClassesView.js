import {useEffect, useState} from "react";
import ClassCard from "./ClassCard";
import RequestCard from "./RequestCard"

function ClassesView() {
    const userFromLocalStorage = JSON.parse(localStorage.getItem("USER")) ?? null;
    const userId = userFromLocalStorage ? userFromLocalStorage.id : null;
    const [courses, setCourses] = useState([]);
    const [showClasses, setShowClasses] = useState(false);
    const [classes, setClasses] = useState([]);
    const [currentCourseName, setCurrentCourseName] = useState("");
    const [rescheduleRequests, setRescheduleRequests] = useState([]);

    useEffect(() => {
        const fetchCourses = async () => {
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
                console.log("Error during loading classes: ", error);
            }
        };

        const fetchRescheduleRequests = async () => {
            try {
                const response = await fetch(`http://localhost:8080/reschedule/${userFromLocalStorage.id}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    console.log("Requests: ", data);
                    setRescheduleRequests(data);
                }
            } catch (error) {
                console.log("Error during reschedule requests fetching: ", error);
            }
        }
        // const fetchSoftwares = () => {
        //     try {
        //         const response = await fetch("http")
        //     }
        // }

        fetchCourses();
        fetchRescheduleRequests();
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
            console.log("Error during loading course's classes: ", error);
        }
    }

    return (
        <div className="p-4">
            <h1>Moje kursy</h1>
            <hr></hr>
            <div className="row flex-wrap mb-5">
                {courses.length > 0 ? (
                    courses.map(course =>
                        <div className="col-6 d-flex p-3">
                            <div className="d-flex px-3 py-2 rounded-2 bg-light" style={{width: "100%"}}>
                                <h4 className="me-5">{course.name}</h4>
                                <p className="me-5">Starosta grupy: {course.studentRep.firstName} {course.studentRep.lastName}</p>
                                <button className="btn btn-secondary ms-auto" onClick={() => showClassesWithinCourse(course.id, course.name)}>Szczegóły kursu</button>
                            </div>
                        </div>
                    )
                ) : (
                    <div className="p-3">
                        <p>Nie prowadzisz żadnych kursów</p>
                    </div>
                )}
            </div>
            {showClasses &&
                <div className="mb-5">
                    <h2>Zajęcia w ramach przedmiotu <i>{currentCourseName}</i>:</h2>
                    <hr></hr>
                    <div className="d-flex flex-wrap">
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
            {rescheduleRequests.length > 0 &&
                <div className="mb-5">
                    <h2>Prośby o zmianę terminu:</h2>
                    <hr></hr>
                    <div className="d-flex flex-wrap">
                        {rescheduleRequests.map(
                            request => <RequestCard request={request} />
                        )
                        }
                    </div>
                </div>}
        </div>
    )
}

export default ClassesView;