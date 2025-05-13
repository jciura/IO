import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import Popup from "reactjs-popup";
import Select from "react-select/base";


function ClassesView() {
    const {user_id} = useParams();
    const [courses, setCourses] = useState([]);
    const [showClasses, setShowClasses] = useState(false);
    const [classes, setClasses] = useState([]);
    const [currentCourseName, setCurrentCourseName] = useState("");

    const [newDate, setNewDate] = useState(new Date());
    const [newClassDuration, setNewClassDuration] = useState(0);
    const [newClassroom, setNewClassroom] = useState("");
    const [newCapacity, setNewCapacity] = useState(0);
    const [computersNeeded, setComputersNeeded] = useState(false);
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [selectedClassroom, setSelectedClassroom] = useState(null);

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

        // const fetchSoftwares = () => {
        //     try {
        //         const response = await fetch("http")
        //     }
        // }

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

    async function handleClassChangeRequest(classId) {
        console.log(classId);
        console.log(newDate, newClassDuration);
    }

    function handleClassroomSelection(selectedOption) {
        console.log("Classroom selected: ", selectedOption);
        setSelectedClassroom(selectedOption);
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
                                    <Popup trigger={<button className="btn btn-secondary ms-auto">Zaproponuj zmianę</button>} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
                                        {
                                            close => (
                                                <div>
                                                    <div className="d-flex p-2">
                                                        {/*<div className="bg-black rounded-2 p-3 border-0">*/}
                                                        {/*    <h4>{classSession.dateTime}</h4>*/}
                                                        {/*</div>*/}
                                                        <div className="d-flex col-6">
                                                            <div className="d-flex flex-column col-6">
                                                                <label className="mb-4">Aktualna data:</label>
                                                                <label className="mb-4">Aktualny czas trwania:</label>
                                                                <label>Aktualna sala:</label>
                                                            </div>
                                                            <div className="d-flex flex-column col-6">
                                                                <p className="mb-4">{classSession.dateTime}</p>
                                                                <p className="mb-4">{classSession.duration}</p>
                                                                <p>{classSession.classroomDto.building}, {classSession.classroomDto.floor}, {classSession.classroomDto.number}</p>
                                                            </div>
                                                        </div>
                                                        <div className="d-flex col-6">
                                                            <div className="d-flex flex-column col-6">
                                                                <label htmlFor="dateInput" className="mb-4"><b>Nowa data:</b></label>
                                                                <label htmlFor="durationInput" className="mb-4"><b>Nowy czas trwania:</b></label>
                                                                <label htmlFor="classroomInput" className="mb-4"><b>Nowa sala:</b></label>
                                                                {/*<label htmlFor="capacityInput" className="mb-4"><b>Wymagana liczba miejsc:</b></label>*/}
                                                                {/*<label htmlFor="computersNeededInput" className="mb-4"><b>Komputery wymagane?:</b></label>*/}

                                                            </div>
                                                            <div className="d-flex flex-column col-6">
                                                                <input id="dateInput" type="datetime-local" className="mb-3"
                                                                       defaultValue={classSession.dateTime}
                                                                       onChange={(event) => setNewDate(new Date(event.target.value))}/>
                                                                <input id="durationInput" type="number" className="mb-3"
                                                                       defaultValue={classSession.duration} min={10} max={150}
                                                                       onChange={(event) => setNewClassDuration(event.target.value)}/>
                                                                    <Select isDisabled={false}
                                                                            onMenuOpen={() => setIsMenuOpen(true)}
                                                                            onMenuClose={() => setIsMenuOpen(false)}
                                                                            menuIsOpen={isMenuOpen}
                                                                            value={selectedClassroom}
                                                                            onInputChange={() => console.log("Input change")}
                                                                            onChange={(selectedOption) => handleClassroomSelection(selectedOption)}
                                                                            options={[{value: "A1", label: "Sala A1"}, {value: "A2", label: "Sala A2"}]}
                                                                            isSearchable={false}
                                                                            isClearable={true}
                                                                    />

                                                                {/*<input id="capacityInput" type="number" className="mb-3 mt-2"*/}
                                                                {/*       defaultValue={classSession.classroomDto.capacity} min={1} max={500}*/}
                                                                {/*       onChange={(event) => setNewCapacity(event.target.value)} />*/}
                                                                {/*<input id="computersNeededInput" type="checkbox" className="mb-3 mt-4"*/}
                                                                {/*       defaultChecked={classSession.classroomDto.hasComputers}*/}
                                                                {/*       onChange={(event) => setComputersNeeded(event.target.value)} />*/}
                                                            </div>
                                                        </div>

                                                    </div>
                                                    <div className="d-flex">
                                                        <button onClick={() => {handleClassChangeRequest(classSession.id); close()}} className="btn btn-outline-secondary ms-auto" disabled>Zaproponuj</button>
                                                    </div>
                                                </div>
                                            )
                                        }
                                    </Popup>
                                </div>
                            </div>
                        )}
                    </div>
                </div>}
        </div>
    )
}

export default ClassesView;