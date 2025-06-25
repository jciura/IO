import Popup from "reactjs-popup";
import 'reactjs-popup/dist/index.css';
import Select from "react-select/base";
import {useEffect, useState} from "react";
import {useClasses} from "./ClassesContext";
import {hover} from "@testing-library/user-event/dist/hover";

function ClassView({event}) {
    // console.log(event)
    const {fetchAll} = useClasses();
    const [newDate, setNewDate] = useState(null);
    const [newClassDuration, setNewClassDuration] = useState(null);
    const [newClassroom, setNewClassroom] = useState(null);
    const [newCapacity, setNewCapacity] = useState(0);
    const [computersNeeded, setComputersNeeded] = useState(false);
    const [isMenuOpen, setIsMenuOpen] = useState(false);
    const [selectedClassroom, setSelectedClassroom] = useState("");
    const [recommendedClassrooms, setRecommendedClassrooms] = useState([]);
    const [isForAllSessions, setIsForAllSessions] = useState(false);

    const [hovered, setHover] = useState(false);

    var areInputsFilled = newDate && newClassDuration && newClassroom;
    const eventDesc = event.desc.split(", ");

    const monthParser = {"01": "stycznia", "02": "lutego", "03": "marca", "04": "kwietnia", "05": "maja", "06": "czerwca",
        "07": "lipca", "08": "sierpnia", "09": "września", "10": "października", "11": "listopada", "12": "grudnia"}

    const currentUser = JSON.parse(localStorage.getItem("USER"));
    console.log(currentUser);
    const currentUserId = localStorage.getItem("USER") != null ? JSON.parse(localStorage.getItem("USER")).id : null;
    useEffect(() => {
        if (event == null)
            return;
        console.log(event);
        if (!event.isRequest) {
            console.log("EVENT:", event);
            setNewDate(event.classSession.dateTime);
            setNewClassDuration(event.classSession.duration);
        } else
            console.log(currentUserId === event.request.requesterId, currentUserId, event.request.requesterId, event.isRequest);
    }, [event, currentUserId])



    function setInitialPopupValues() {
        setNewDate(event.classSession.dateTime);
        setNewClassDuration(event.classSession.duration);
        setSelectedClassroom(null);
        setNewClassroom(null);
    }

    async function handleClassChangeRequest(close) {
        try {
            const response = await fetch(
                `http://localhost:8080/reschedule/request/${currentUserId}`,
                {
                    method: "POST",
                    headers: {
                        "Content-Type": "application/json"
                    },
                    body: JSON.stringify({
                        classSessionDto: event.classSession,
                        requesterId: currentUserId,
                        newClassroom: newClassroom,
                        newDateTime: newDate,
                        newDuration: newClassDuration,
                        oldTime: event.classSession.dateTime,
                        status: "PENDING",
                        isForAllSessions: isForAllSessions
                    })
            });

            if (response.ok) {
                console.log("Request sent");
                await fetchAll();
                close();
            }
            else {
                const errorText = await response.text();
                console.log("ERROR:", errorText);
                alert(errorText);
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
                    classroomDto: event.classSession.classroomDto,
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

    async function handleRequestAccept(){
        try {
            console.log(event);
            const response = await fetch(`http://localhost:8080/reschedule/${event.request.id}/accept`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                // window.location.reload();
                await fetchAll();
                console.log("Request accepted.");
            }
        } catch (error) {
            console.log("Error during accepting request: ", error);
        }
    }

    async function handleRequestReject() {
        try {
            const response = await fetch(`http://localhost:8080/reschedule/${event.request.id}/reject`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                // window.location.reload();
                await fetchAll();
                console.log("Request rejected.");
            }
        } catch (error) {
            console.log("Error during accepting request: ", error);
        }
    }

    async function handleRequestDelete() {
        try {
            const response = await fetch(`http://localhost:8080/reschedule/${event.request.id}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                // window.location.reload();
                await fetchAll();
                console.log("Request successfully deleted.");
            }
        } catch (error) {
            console.log("Error during deleting request: ", error);
        }
    }

    function checkIfClassSessionWillHappen() {
        return event.start > new Date();
    }

    function parseDateTime(term) {
        let month = term.slice(5, 7);

        return String(Number(term.slice(8, 10))) + " " + monthParser[month] + " " + term.slice(0, 4);
    }

    return (
        <div className="d-flex flex-row justify-content-between"
             onMouseEnter={() => setHover(true)}
             onMouseLeave={() => setHover(false)}
             style={{
                     height: '100%',
                     width: '100%',
                     border: `1px solid`,
                     borderColor: hovered ? 'black' : 'transparent',
                     //backgroundColor: hovered ? '#cce5ff' : event.color || '#ddd',
                     backgroundColor: event.color,
                     filter: hovered ? 'brightness(1.15)' : 'brightness(1)',
                     color: '#000',
                     padding: '4px 6px',
                     borderRadius: '4px',
                     transition: 'all 0.2s ease',
                     wordWrap: 'break-word',
                     whiteSpace: 'normal',
                 }}>
            {event.isRequest === false ? (
                <Popup trigger={<div className="p-2 w-100">
                    <strong className="mt-2 mb-2">{event.title}</strong>
                    {event.desc && <div className="mt-2 mb-2">{event.desc}</div>}
                </div>} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested onClose={()=>setHover(false)}>
                    {
                        close => (
                            <div className="p-3">
                                <h1 style={{color: event.color, filter: 'brightness(0.9)'}}>{event.title}</h1>
                                <hr/>
                                <div className="row">
                                    <div className="col-4">
                                        <p><b>Budynek: </b>{eventDesc[0]}</p>
                                        <p><b>Sala: </b>{eventDesc[2]}</p>
                                        <p><b>Piętro: </b>{eventDesc[1]}</p>
                                        <p><b>Pojemność: </b>{event.classSession.classroomDto.capacity}</p>
                                    </div>
                                    <div className="col">
                                        <p><b>Data: </b>{parseDateTime(event.classSession.dateTime)}</p>
                                        <p><b>Godzina rozpoczęcia: </b>{event.classSession.dateTime.slice(11, 16)}</p>
                                        <p><b>Czas trwania: </b>{event.classSession.duration} min</p>
                                        <p><b>Starosta grupy: </b>{event.classSession.classRep.firstName}
                                            {event.classSession.classRep.lastName}, {event.classSession.classRep.email}</p>
                                    </div>
                                </div>

                                <div className="d-flex justify-content-between w-100">
                                {checkIfClassSessionWillHappen() && currentUser.role === "PROWADZACY" && (
                                    <Popup trigger={<button className="btn btn-secondary">Zaproponuj zmianę</button>} onOpen={() => setInitialPopupValues()} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
                                        {
                                            close => (
                                                <div>
                                                    <div className="d-flex p-2">
                                                        <div className="d-flex col-6">
                                                            <div className="d-flex flex-column col-6">
                                                                <label className="mb-4">Aktualna data:</label>
                                                                <label className="mb-4">Aktualny czas trwania:</label>
                                                                <label>Aktualna sala:</label>
                                                            </div>
                                                            <div className="d-flex flex-column col-6">
                                                                <p className="mb-4">{event.classSession.dateTime}</p>
                                                                <p className="mb-4">{event.classSession.duration}</p>
                                                                <p>{event.classSession.classroomDto.building}, {event.classSession.classroomDto.floor}, {event.classSession.classroomDto.number}</p>
                                                            </div>
                                                        </div>
                                                        <div className="d-flex col-6">
                                                            <div className="d-flex flex-column col-6">
                                                                <label htmlFor="dateInput" className="mb-4"><b>Nowa data:</b></label>
                                                                <label htmlFor="durationInput" className="mb-4"><b>Nowy czas trwania:</b></label>
                                                                <label htmlFor="classroomInput" className="mb-4"><b>Nowa sala:</b></label>
                                                            </div>
                                                            <div className="d-flex flex-column col-6">
                                                                <input id="dateInput" type="datetime-local" className="mb-3"
                                                                       defaultValue={event.classSession.dateTime}
                                                                       onChange={(event) => setNewDate(event.target.value)}/>
                                                                <input id="durationInput" type="number" className="mb-3"
                                                                       defaultValue={event.classSession.duration} min={10} max={150}
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
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div className="form-check mt-2">
                                                        <input
                                                            className="form-check-input"
                                                            type="checkbox"
                                                            id="forAllSessionsCheck"
                                                            checked={isForAllSessions}
                                                            onChange={(e) => setIsForAllSessions(e.target.checked)}
                                                        />
                                                        <label className="form-check-label" htmlFor="forAllSessionsCheck">
                                                            Zastosuj do wszystkich sesji zajęć
                                                        </label>
                                                    </div>
                                                    <div className="d-flex">
                                                        <button onClick={() => {handleClassChangeRequest(); close()}} disabled={!areInputsFilled} className="btn btn-secondary ms-auto">Zaproponuj</button>
                                                    </div>
                                                </div>
                                            )
                                        }
                                    </Popup>
                                )}
                                <button className="btn btn-outline-info ms-auto" onClick={close}>Zamknij</button>
                                </div>
                            </div>
                        )}
                </Popup>
            ) : (
                <div>
                    <div>
                        <strong className="mt-2 mb-2">{event.title}</strong>
                        {event.desc && <div className="mt-2 mb-2">{event.desc}</div>}
                    </div>
                    <div className="d-flex">
                        {event.request.requesterId === currentUserId ? (
                            <Popup trigger={<button className="btn btn-danger ms-auto">Usuń</button>} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
                                {
                                    close => (
                                        <div className="p-3">
                                            <h1>Czy na pewno chcesz usunąć tę propozycję?</h1>
                                            <hr></hr>
                                            <div className="d-flex">
                                                <button onClick={close} className="btn btn-outline-success ms-auto">Nie</button>
                                                <button onClick={() => {handleRequestDelete(); close()}} className="btn btn-danger ms-3">Tak</button>
                                            </div>
                                        </div>
                                    )
                                }
                            </Popup>
                        ) : (
                            <div className="d-flex justify-content-end">
                                <Popup trigger={<button className="btn btn-primary">Zdecyduj</button>} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
                                    {
                                        close => (
                                            <div className="p-3">
                                                <h1>Jaka jest Twoja decyzja dotycząca tej propozycji?</h1>
                                                {event.request.isForAllSessions === true && <p className="text-warning"><b>Dotyczy wszystkich sesji tego przedmiotu</b></p>}
                                                <hr></hr>
                                                <div className="d-flex">
                                                    <button onClick={() => {handleRequestAccept(); close()}} className="btn btn-success ms-auto">Akceptuję</button>
                                                    <button onClick={() => {handleRequestReject(); close()}} className="btn btn-danger ms-3">Odrzucam</button>
                                                    <button onClick={close} className="btn btn-secondary ms-3">Jeszcze się zastanowię</button>
                                                </div>
                                            </div>
                                        )
                                    }
                                </Popup>
                            </div>
                        )}
                    </div>
                </div>
            )
                    }
                </div>
    )
}

export default ClassView;