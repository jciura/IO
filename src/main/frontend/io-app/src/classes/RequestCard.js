import Select from "react-select/base";


function RequestCard({request}) {

    return (
        <div className="col-4 p-2">
            <div className="px-3 py-2 rounded-2 bg-info">
                <div className="mb-3">
                    <h4><b>Przedmiot:</b> {request.classSessionDto.courseName}</h4>
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
            </div>
        </div>
    )
}

export default RequestCard;