import Popup from "reactjs-popup";
import Select from 'react-select';
import {useState} from "react";

function ClassCard({classSession}) {

    const [newDate, setNewDate] = useState(classSession.dateTime);
    const [newClassDuration, setNewClassDuration] = useState(classSession.duration);
    const [newClassroom, setNewClassroom] = useState(null);
    const [newCapacity, setNewCapacity] = useState(0);
    const [computersNeeded, setComputersNeeded] = useState(false);
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [selectedClassroom, setSelectedClassroom] = useState("");
    const [recommendedClassrooms, setRecommendedClassrooms] = useState([]);
    const [isForAllSessions, setIsForAllSessions] = useState(false);


    var areInputsFilled = newDate && newClassDuration && newClassroom;

    function setInitialPopupValues() {
        alert("HUJ");
        setNewDate(classSession.dateTime);
        setNewClassDuration(classSession.duration);
        setSelectedClassroom(null);
        setNewClassroom(null);
    }

    function checkIfClassSessionIsPast() {
        return new Date(classSession.dateTime) > new Date();
    }

    async function handleClassChangeRequest() {
        try {
            console.log(classSession.dateTime, "HUJ");
            const response = await fetch(
                `http://localhost:8080/reschedule/request/${JSON.parse(localStorage.getItem("USER")).id}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        classSessionDto: classSession,
                        requesterId: JSON.parse(localStorage.getItem("USER")).id,
                        newClassroom: newClassroom,
                        newDateTime: newDate,
                        newDuration: newClassDuration,
                        oldTime: classSession.dateTime,
                        status: "PENDING",
                        isForAllSessions: isForAllSessions
                    })
                });

            if (response.ok) {
                console.log("Request sent");
                window.location.reload();
            }
            else {
                console.log("ERROR");
            }
        } catch (error) {
            console.log("Error during reschedule request: ", error);
        }
        console.log(newDate, newClassDuration);
    }

    async function loadClassroomRecommendations() {
        console.log("CHECK: ", newDate, newClassDuration, newClassroom, "FLAG: ", areInputsFilled);
        try {
            const response = await fetch("http://localhost:8080/recommendations/getRecommendations", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    classroomDto: classSession.classroomDto,
                    dateTime: newDate,
                    duration: newClassDuration
                })
            });
            if (response.ok) {
                const data = await response.json();
                var classrooms = [];
                data.map(recommendation => {
                    console.log("Recommendation: ", recommendation);
                    classrooms.push(
                        {
                            value: recommendation.classroomDto,
                            label: (recommendation.classroomDto.building + ", "
                                + recommendation.classroomDto.floor + ", "
                                + recommendation.classroomDto.number)
                        })
                });
                console.log("Classrooms: ", classrooms);
                setRecommendedClassrooms(classrooms);
            }
        } catch (error) {
            console.log("Error during loading classroom recommendations: ", error);
        }
    }

    function handleClassroomSelection(selectedOption) {
        console.log("Classroom selected: ", selectedOption);
        if (selectedOption)
            setNewClassroom(selectedOption.value);
        else
            setNewClassroom(null);
        setSelectedClassroom(selectedOption);
    }

    return (
        <div className="col-4 p-2">
            <div className="px-3 py-2 rounded-2 bg-info">
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
                    {checkIfClassSessionIsPast() && (
                        <Popup
                            trigger={<button className="btn btn-secondary ms-auto">Zaproponuj zmianę</button>}
                            onOpen={() => setInitialPopupValues()}
                            contentStyle={{ maxWidth: "50%" }}
                            className="bg-light rounded-2"
                            modal
                            nested
                        >
                            {close => (
                                <div className="p-3">
                                    <div className="row">
                                        <div className="col-6">
                                            <div className="mb-3"><strong>Aktualna data:</strong> {classSession.dateTime}</div>
                                            <div className="mb-3"><strong>Aktualny czas trwania:</strong> {classSession.duration}</div>
                                            <div><strong>Aktualna sala:</strong> {classSession.classroomDto.building}, {classSession.classroomDto.floor}, {classSession.classroomDto.number}</div>
                                        </div>
                                        <div className="col-6">
                                            <div className="mb-3">
                                                <label htmlFor="dateInput" className="form-label"><b>Nowa data:</b></label>
                                                <input
                                                    id="dateInput"
                                                    type="datetime-local"
                                                    className="form-control"
                                                    defaultValue={classSession.dateTime}
                                                    onChange={(event) => setNewDate(event.target.value)}
                                                />
                                            </div>
                                            <div className="mb-3">
                                                <label htmlFor="durationInput" className="form-label"><b>Nowy czas trwania:</b></label>
                                                <input
                                                    id="durationInput"
                                                    type="number"
                                                    className="form-control"
                                                    defaultValue={classSession.duration}
                                                    min={10}
                                                    max={150}
                                                    onChange={(event) => setNewClassDuration(event.target.value)}
                                                />
                                            </div>
                                            <div className="mb-3">
                                                <label htmlFor="classroomInput" className="form-label"><b>Nowa sala:</b></label>
                                                <Select
                                                    onMenuOpen={() => { setIsMenuOpen(true); loadClassroomRecommendations(); }}
                                                    onMenuClose={() => setIsMenuOpen(false)}
                                                    menuIsOpen={isMenuOpen}
                                                    value={selectedClassroom}
                                                    onChange={(selectedOption) => handleClassroomSelection(selectedOption)}
                                                    options={recommendedClassrooms}
                                                    isSearchable={false}
                                                    isClearable={true}
                                                />
                                            </div>
                                        </div>
                                    </div>

                                    <div className="form-check mt-4">
                                        <input
                                            className="form-check-input"
                                            type="checkbox"
                                            id="allSessionsCheckbox"
                                            checked={isForAllSessions}
                                            onChange={(event) => setIsForAllSessions(event.target.checked)}
                                        />
                                        <label className="form-check-label" htmlFor="allSessionsCheckbox">
                                            Zmiana dla wszystkich zajęć?
                                        </label>
                                    </div>
                                    <div style={{ color: "red" }}>TEST: działa?</div>


                                    <div className="d-flex justify-content-end mt-4">
                                        <button
                                            onClick={() => { handleClassChangeRequest(); close(); }}
                                            disabled={!areInputsFilled}
                                            className="btn btn-secondary"
                                        >
                                            Zaproponuj
                                        </button>
                                    </div>
                                </div>
                            )}
                        </Popup>
                    )}
                </div>
            </div>
        </div>
    )
}

export default ClassCard;