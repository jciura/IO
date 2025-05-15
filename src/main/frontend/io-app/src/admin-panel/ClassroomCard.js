import Popup from "reactjs-popup";
import {useState} from "react";

function ClassroomCard({classroom}) {
    var backgroundColor;
    const [softwareList, setSoftwareList] = useState([]);

    if (classroom.hasComputers === true) {
        backgroundColor = "lightgreen";
    } else {
        backgroundColor = "indianred";
    }

    async function handleShowSoftware(classroomId) {
        try {
            const response = await fetch(`http://localhost:8080/classrooms/${classroomId}`, {
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

    return (
        <div className="px-3 py-2 rounded" style={{backgroundColor: backgroundColor}}>
            <div className="">
                <h2>{classroom.number}</h2>
                <h4>Budynek: {classroom.building}</h4>
                <p><b>Piętro:</b> {classroom.floor}<br/>
                    <b>Pojemność:</b> {classroom.capacity}<br/>
                </p>
            </div>
            <div className="d-flex mb-2">
                <Popup trigger={<button className="btn btn-secondary ms-auto">Pokaż oprogramowanie</button>} className="bg-light rounded-2" modal nested
                       onOpen={() => handleShowSoftware(classroom.id)}>
                    {
                        close => {
                            return (
                                <div>
                                    <div className="p-2">
                                        <h3>Oprogramowania dostępne w sali {classroom.number} w
                                            budynku {classroom.building}</h3>
                                        <hr></hr>
                                        <ul>
                                            {softwareList.map((software, index) => (
                                                <li key={index}>{software}</li>
                                            ))}
                                        </ul>
                                        <button onClick={() => close()} className="btn btn-outline-secondary">Zamknij</button>
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