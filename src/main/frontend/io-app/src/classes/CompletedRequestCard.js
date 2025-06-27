function CompletedRequestCard({request}) {
    return (
        <div className="col-4 p-2">
            <div className={request.status === "ACCEPTED" ? "p-3 rounded-2 bg-success" : "p-3 rounded-2 bg-danger"}>
                <div className="mb-3">
                    <h4><b>Przedmiot:</b> <i>{request.classSessionDto.courseName}</i></h4>
                </div>
                <div className="d-flex p-2">
                    <div className="d-flex col-6">
                        <div className="d-flex flex-column col-6">
                            <label className="mb-4">Stara data:</label>
                            <label className="mb-4">Stara czas trwania:</label>
                            <label>Stara sala:</label>
                        </div>
                        <div className="d-flex flex-column col-6">
                            <p className="mb-4">{request.status === "ACCEPTED" ? request.oldTime : request.classSessionDto.dateTime}</p>
                            <p className="mb-4">{request.status === "ACCEPTED" ? request.oldDuration : request.classSessionDto.duration}</p>
                            <p>{request.status === "ACCEPTED" ?
                                `${request.oldClassroom.building}, ${request.oldClassroom.floor}, ${request.oldClassroom.number}`
                                : `${request.classSessionDto.classroomDto.building}, ${request.classSessionDto.classroomDto.floor}, ${request.classSessionDto.classroomDto.number}`}</p>
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
            </div>
        </div>
    )
}

export default CompletedRequestCard;