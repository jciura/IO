import Popup from "reactjs-popup";

function RequestCard({request}) {
    const currentUserId = JSON.parse(localStorage.getItem("USER")).id;

    async function handleRequestDelete() {
        try {
            const response = await fetch(`http://localhost:8080/reschedule/${request.id}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                window.location.reload();
                console.log("Request successfully deleted.");
            }
        } catch (error) {
            console.log("Error during deleting request: ", error);
        }
    }

    async function handleRequestAccept() {
        try {
            const response = await fetch(`http://localhost:8080/reschedule/${request.id}/accept`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                window.location.reload();
                console.log("Request accepted.");
            }
        } catch (error) {
            console.log("Error during accepting request: ", error);
        }
    }

    async function handleRequestReject() {
        try {
            const response = await fetch(`http://localhost:8080/reschedule/${request.id}/reject`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                window.location.reload();
                console.log("Request rejected.");
            }
        } catch (error) {
            console.log("Error during accepting request: ", error);
        }
    }

    return (
        <div className="col-4 p-2">
            <div className="px-3 py-3 rounded-2 bg-info">
                <div className="mb-3">
                    <h4><b>Przedmiot:</b> <i>{request.classSessionDto.courseName}</i></h4>
                    {request.isForAllSessions && (
                        <p className="text-warning"><b>Dotyczy wszystkich sesji tego przedmiotu</b></p>
                    )}
                </div>
                <div className="d-flex p-2">
                    <div className="d-flex col-6">
                        <div className="d-flex flex-column col-6">
                            <label className="mb-4">Aktualna data:</label>
                            <label className="mb-4">Aktualny czas trwania:</label>
                            <label>Aktualna sala:</label>
                        </div>
                        <div className="d-flex flex-column col-6">
                            <p className="mb-4">{request.classSessionDto.dateTime}</p>
                            <p className="mb-4">{request.classSessionDto.duration}</p>
                            <p>{request.classSessionDto.classroomDto.building}, {request.classSessionDto.classroomDto.floor}, {request.classSessionDto.classroomDto.number}</p>
                        </div>
                    </div>
                    <div className="d-flex col-6">
                        <div className="d-flex flex-column col-6">
                            <label className="mb-4"><b>Nowa data:</b></label>
                            <label className="mb-4"><b>Nowy czas trwania:</b></label>
                            <label className="mb-4"><b>Nowa sala:</b></label>
                        </div>
                        <div className="d-flex flex-column col-6">
                            <p className="mb-4">{request.newDateTime}</p>
                            <p className="mb-4">{request.newDuration}</p>
                            <p>{request.newClassroom.building}, {request.newClassroom.floor}, {request.newClassroom.number}</p>
                        </div>
                    </div>
                </div>
                <div className="d-flex">
                    {request.requesterId === currentUserId ? (
                        <Popup trigger={<button className="btn btn-danger ms-auto">Usuń propozycję</button>} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
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
                            <Popup trigger={<button className="btn btn-success">Akceptuj propozycję</button>} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
                            {
                                close => (
                                    <div className="p-3">
                                        <h1>Czy na pewno potwierdzasz akceptację tej propozycji?</h1>
                                        <hr></hr>
                                        <div className="d-flex">
                                            <button onClick={close} className="btn btn-danger ms-auto">Nie</button>
                                            <button onClick={() => {handleRequestAccept(); close()}} className="btn btn-success ms-3">Tak</button>
                                        </div>
                                    </div>
                                )
                            }
                            </Popup>
                            <Popup trigger={<button className="btn btn-danger ms-3">Odrzuć propozycję</button>} contentStyle={{maxWidth: "50%"}} className="bg-light rounded-2" modal nested>
                            {
                                close => (
                                    <div className="p-3">
                                        <h1>Czy na pewno potwierdzasz odrzucenie tej propozycji?</h1>
                                        <hr></hr>
                                        <div className="d-flex">
                                            <button onClick={close} className="btn btn-outline-success ms-auto">Nie</button>
                                            <button onClick={() => {handleRequestReject(); close()}} className="btn btn-danger ms-3">Tak</button>
                                        </div>
                                    </div>
                                )
                            }
                            </Popup>
                        </div>

                    )
                    }

                </div>
            </div>
        </div>
    )
}

export default RequestCard;
