import Popup from "reactjs-popup";
import Select from "react-select/base";
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

    var areInputsFilled = newDate && newClassDuration && newClassroom;

    function setInitialPopupValues() {
        setNewDate(classSession.dateTime);
        setNewClassDuration(classSession.duration);
        setSelectedClassroom(null);
        setNewClassroom(null);
    }

    function checkIfClassSessionIsPast() {
        return new Date(classSession.dateTime) > new Date();
    }

    //TODO: Finish
    async function handleClassChangeRequest() {
        try {
            const response = await fetch(
                `http://localhost:8080/reschedule/request/${JSON.parse(localStorage.getItem("USER")).id}/single`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        classSessionDto: classSession,
                        newClassroom: newClassroom,
                        newDateTime: newDate,
                        status: "PENDING",
                        isForAllSessions: false
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
                        <Popup trigger={<button className="btn btn-secondary ms-auto">Zaproponuj zmianę</button>} onOpen={() => setInitialPopupValues()} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
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
                                                           onChange={(event) => setNewDate(event.target.value)}/>
                                                    <input id="durationInput" type="number" className="mb-3"
                                                           defaultValue={classSession.duration} min={10} max={150}
                                                           onChange={(event) => setNewClassDuration(event.target.value)}/>
                                                    <Select isDisabled={false}
                                                            onMenuOpen={() => {setIsMenuOpen(true); loadClassroomRecommendations()}}
                                                            onMenuClose={() => setIsMenuOpen(false)}
                                                            menuIsOpen={isMenuOpen}
                                                            value={selectedClassroom}
                                                            onInputChange={() => console.log("Input change")}
                                                            onChange={(selectedOption) => handleClassroomSelection(selectedOption)}
                                                            // options={[{value: "A1", label: "Sala A1"}, {value: "A2", label: "Sala A2"}]}
                                                            options={recommendedClassrooms}
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
                                            <button onClick={() => {handleClassChangeRequest(); close()}} disabled={!areInputsFilled} className="btn btn-secondary ms-auto">Zaproponuj</button>
                                        </div>
                                    </div>
                                )
                            }
                        </Popup>
                    )}
                </div>
            </div>
        </div>
    )
}

export default ClassCard;