import Popup from "reactjs-popup";
import Select from "react-select/base";
import {useEffect, useState} from "react";
import {useClasses} from "./ClassesContext";

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

    var areInputsFilled = newDate && newClassDuration && newClassroom;

    const currentUser = JSON.parse(localStorage.getItem("USER"));
    console.log(currentUser);
    const currentUserId = localStorage.getItem("USER") != null ? JSON.parse(localStorage.getItem("USER")).id : null;
    useEffect(() => {
        if (event == null)
            return;
        // console.log(event);
        if (!event.isRequest) {
            // console.log("EVENT:", event);
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
                        status: "PENDING",
                        isForAllSessions: isForAllSessions
                    })
            });

            if (response.ok) {
                console.log("Request sent");
                // window.location.reload();
                await fetchAll();
                close();
            }
            else {
                console.log("ERROR");
                alert("Masz już inne zajęcia w tym terminie!");
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

    function checkIfClassSessionIsPast() {
        return event.start > new Date();
    }

    return (
        <div className="d-flex flex-row justify-content-between">
            <div style={{width: 100, marginRight: 20}}>
                <strong className="mt-2 mb-2">{event.title}</strong>
                {event.desc && <div className="mt-2">{event.desc}</div>}
            </div>
            {event.isRequest === true ? (
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
                            {/*<Popup trigger={<button className="btn btn-danger ms-3">Odrzuć</button>} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>*/}
                            {/*    {*/}
                            {/*        close => (*/}
                            {/*            <div className="p-3">*/}
                            {/*                <h1>Czy na pewno potwierdzasz odrzucenie tej propozycji?</h1>*/}
                            {/*                <hr></hr>*/}
                            {/*                <div className="d-flex">*/}
                            {/*                    <button onClick={close} className="btn btn-outline-success ms-auto">Nie</button>*/}
                            {/*                    <button onClick={() => handleRequestReject(); close()}} className="btn btn-danger ms-3">Tak</button>*/}
                            {/*                </div>*/}
                            {/*            </div>*/}
                            {/*        )*/}
                            {/*    }*/}
                            {/*</Popup>*/}
                        </div>
                    )}
                </div>
            ) : (
                <div>
                    {checkIfClassSessionIsPast() && currentUser.role === "PROWADZACY" && (
                        <Popup trigger={<button className="btn btn-secondary ms-auto">Zaproponuj zmianę</button>} onOpen={() => setInitialPopupValues()} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
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
                                            <button onClick={() => handleClassChangeRequest(close)} disabled={!areInputsFilled} className="btn btn-secondary ms-auto">Zaproponuj</button>
                                        </div>
                                    </div>
                                )
                            }
                        </Popup>
                    )
                    }
                </div>
                )
            }
        </div>
    )
}

export default ClassView;