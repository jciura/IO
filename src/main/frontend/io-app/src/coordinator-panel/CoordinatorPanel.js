import {useRef, useState} from "react";


function CoordinatorPanel() {
    const [isScheduleUploaded, setIsScheduleUploaded] = useState(false);
    const fileInputRef = useRef(null);

    const handleFileChange = (event) => {
        const file = event.target.files[0];

        if (!file) {
            setIsScheduleUploaded(false);
            return;
        }
        if (file.type !== "text/csv" && !file.name.endsWith(".csv")) {
            alert("Proszę wybrać plik csv!");
            setIsScheduleUploaded(false);
            return;
        }
        setIsScheduleUploaded(true);
    }

    const handleFileImport = async () => {
        try {
            const file = fileInputRef.current.files[0];
            if (!file) {
                alert("Nie wybrano pliku.");
                return;
            }

            const formData = new FormData();
            formData.append("file", file);
            const response = await fetch("http://localhost:8080/timetable/upload", {
                method: "POST",
                body: formData
            });

            if (response.ok) {
                console.log("Timetable successfully imported");
                alert("Plan zajęć został pomyślnie zaimportowany.");
                const data = await response.text();
                console.log(data);
            }
            else {
                console.log("Error during schedule import");
            }
        } catch (e) {
            console.log("ERROR:", e);
            alert("Wystąpił błąd podczas przesyłania pliku.");
        }
    }

    return (
        <div className="p-5">
            <h2>Wczytaj plan zajęć</h2>
            <hr></hr>
            <div className="d-flex col mb-3">
                <label htmlFor="fileInput" className="me-2">Wybierz plik:</label>
                <input
                    id="fileInput"
                    type="file"
                    accept=".csv"
                    ref={fileInputRef}
                    onChange={(event) => handleFileChange(event)}/>
            </div>
            <button className="btn btn-primary" disabled={!isScheduleUploaded} onClick={() => handleFileImport()}>Importuj plan</button>
        </div>
    )
}

export default CoordinatorPanel;