import Popup from "reactjs-popup";

function RequestCard({request}) {

    async function handleRequestDelete() {
        // try {
        //     const response = await fetch("http://localhost:8080/")
        // }
    }

    return (
        <div className="col-4 p-2">
            <div className="px-3 py-3 rounded-2 bg-info">
                <div className="mb-3">
                    <h4><b>Przedmiot:</b> <i>{request.classSessionDto.courseName}</i></h4>
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
                </div>
            </div>
        </div>
    )
}

export default RequestCard;