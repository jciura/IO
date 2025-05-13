import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";


function ClassesView() {
    const {user_id} = useParams();
    const [courses, setCourses] = useState([]);
    const [showClasses, setShowClasses] = useState(false);
    const [classes, setClasses] = useState([]);
    const [currentCourseName, setCurrentCourseName] = useState("");

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await fetch(`http://localhost:8080/courses/user/${user_id}`, {
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

        fetchData();
    }, [user_id]);

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
            <div className="d-flex mb-5">
                {courses.map(course =>
                    <div className="col-6 d-flex me-3 px-3 py-2 rounded-2 bg-light">
                        <h4 className="me-5">{course.name}</h4>
                        <p className="me-5">Starosta grupy: {course.studentRep.firstName} {course.studentRep.lastName}</p>
                        <button className="btn btn-secondary" onClick={() => showClassesWithinCourse(course.id, course.name)}>Szczegóły kursu</button>
                    </div>
                )}
            </div>
            {showClasses &&
                <div>
                    <h2>Zajęcia w ramach przedmiotu <i>{currentCourseName}</i>:</h2>
                    <hr></hr>
                    <div className="d-flex">
                        {classes.map(classSession =>
                            <div className="col-4 me-2 px-3 py-2 rounded-2 bg-info">
                                <div className="mb-3">
                                    <b>Data:</b> {classSession.dateTime},
                                    <b> Czas trwania zajęć:</b> {classSession.duration}
                                </div>
                                <div className="mb-3">
                                    <b>Budynek:</b> {classSession.classroomDto.building},
                                    <b> Piętro:</b> {classSession.classroomDto.floor},
                                    <b> Sala:</b> {classSession.classroomDto.number}
                                </div>
                                <div className="d-flex">
                                    <button className="btn btn-secondary ms-auto" disabled>Zaproponuj zmianę</button>
                                </div>
                            </div>
                        )}
                    </div>
                </div>}
        </div>
    )
}

export default ClassesView;