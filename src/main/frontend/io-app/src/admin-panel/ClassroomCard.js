import Popup from "reactjs-popup";
import {useState} from "react";
import {API_URL} from "../config";

function ClassroomCard({classroom, onUpdate}) {
    var backgroundColor;
    const [softwareList, setSoftwareList] = useState([]);
    const [newSoftware, setNewSoftware] = useState([]);

    if (classroom.hasComputers === true) {
        backgroundColor = "lightgreen";
    } else {
        backgroundColor = "lightgoldenrodyellow";
    }

    async function handleShowSoftware(classroomId) {
        try {
            const response = await fetch(`${API_URL}/classrooms/${classroomId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            });
            if (response.ok) {
                const data = await response.json();
                setSoftwareList(data.softwareNames);
            }
        } catch (error) {
            console.log(error);
        }

    }

    async function handleDeleteClassroom(classroomId) {
        try {
            const response = await fetch(`${API_URL}/classrooms/${classroomId}`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json"
                }
            });
            if (response.ok) {
                console.log("Sala usunięta");
                if (onUpdate) {
                    onUpdate();
                }
            }
        } catch (error) {
            console.log(error);
        }
    }

    async function handleAddSoftware(classroomId) {
        try {
            console.log(classroomId, newSoftware);
            const response = await fetch(`${API_URL}/classrooms/${classroomId}/software/${newSoftware}`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({
                    softwareName: newSoftware,
                    classroomId: classroomId
                })
            });
            if (response.ok) {
                console.log("Dodano: ", newSoftware);
                handleShowSoftware(classroomId);
            }
        } catch (error) {
            console.log("Error while adding software to classroom: ", error);
        }
    }

    return (
        <div className="px-3 py-2 rounded h-100" style={{backgroundColor: backgroundColor}}>
            <div className="">
                <h2>{classroom.number}</h2>
                <h4>Budynek: {classroom.building}</h4>
                <p><b>Piętro:</b> {classroom.floor}<br/>
                    <b>Pojemność:</b> {classroom.capacity}<br/>
                </p>
            </div>
            <div className="d-flex justify-content-end gap-2 mb-2">
                {classroom.hasComputers === true ? (
                    <Popup trigger={<button className="btn btn-secondary ms-auto">Pokaż oprogramowanie</button>} modal nested
                           onOpen={() => handleShowSoftware(classroom.id)}>
                        {
                            close => {
                                return (
                                    <div>
                                        <div className="p-2">
                                            <h3>Oprogramowania dostępne w sali {classroom.number} w
                                                budynku {classroom.building}</h3>
                                            <hr></hr>
                                            {softwareList.length > 0 ? (
                                                <ul>
                                                    {softwareList.map((software, index) => (
                                                        <li key={index}>{software}</li>
                                                    ))}
                                                </ul>
                                            ) : (
                                                <p><i>W tej sali nie są dostępne żadne oprogramowania!</i></p>
                                            )}
                                            <div className="d-flex gap-2">
                                                <Popup trigger={<button className="btn btn-secondary">Dodaj oprogramowanie</button>} modal>
                                                    {
                                                        close => (
                                                            <div>
                                                                <div className="d-flex gap-2 p-2">
                                                                    {/*<h3>Wybierz spośród dostępnych</h3>*/}
                                                                    {/*<ul>*/}
                                                                    {/*    {}*/}
                                                                    {/*</ul>*/}
                                                                    <label htmlFor="softwareInput"><b>Wpisz
                                                                        nazwę:</b></label>
                                                                    <input id="softwareInput" type="text"
                                                                           onChange={(event) => setNewSoftware(event.target.value)}/>
                                                                </div>
                                                                <button onClick={() => {handleAddSoftware(classroom.id); close()}} className="btn btn-secondary">Dodaj</button>
                                                            </div>
                                                        )
                                                    }

                                                </Popup>
                                                <button onClick={() => close()}
                                                        className="btn btn-outline-secondary ms-auto">Zamknij
                                                </button>
                                            </div>
                                        </div>

                                    </div>
                                )
                            }
                        }
                    </Popup>
                ) : (
                    <div></div>
                )}
                <Popup trigger={<button className="btn btn-outline-danger">Usuń</button>} modal nested>
                    {
                        close => {
                            return (
                                <div>
                                    <div className="p-2">
                                        <h3>Czy na pewno chcesz usunąć salę {classroom.number} z budynku {classroom.building}?</h3>
                                        <div className="d-flex gap-2">
                                            <button className="btn btn-danger ms-auto"
                                                    onClick={() => {handleDeleteClassroom(classroom.id); close()}}>Tak</button>
                                            <button className="btn btn-secondary"
                                                    onClick={()=>close()}>Nie</button>
                                        </div>
                                    </div>
                                </div>
                            )
                        }
                    }
                </Popup>
            </div>



        </div>
    )
}

export default ClassroomCard;