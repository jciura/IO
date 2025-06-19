import {useContext, createContext, useState, useEffect} from "react";

export const ClassesContext = createContext();

export const ClassesProvider = ({ userId, children }) => {
    const [classesEvents, setClassesEvents] = useState([]);

    const fetchRescheduleRequests = async (events) => {
        try {
            const response = await fetch(`http://localhost:8080/reschedule/${userId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                const data = await response.json();
                console.log("Requests: ", data);

                for (let request of data) {
                    events.push({
                        title: request.classSessionDto.courseName,
                        start: new Date(request.newDateTime),
                        end: new Date(new Date(request.newDateTime).getTime() + request.newDuration * 60000),
                        desc: request.newClassroom.building + ", " + request.newClassroom.floor + ", " + request.newClassroom.number,
                        color: "orange",
                        isRequest: true,
                        request: {
                            id: request.id,
                            requesterId: request.requesterId
                        }
                    })
                }
                console.log("Events with requests: ", events);
            }
        } catch (error) {
            console.log("Error during reschedule requests fetching: ", error);
        }
    }

    const fetchCompletedRescheduleRequests = async (events) => {
        try {
            const response = await fetch(`http://localhost:8080/reschedule/completed/${userId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                const data = await response.json();
                console.log("Requests: ", data);
            }
        } catch (error) {
            console.log("Error during completed reschedule requests fetching: ", error);
        }
    }

    const fetchClasses = async (events) => {
        if (userId == null)
            return
        try {
            const response = await fetch(`http://localhost:8080/classes/user_id/${userId}`, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json"
                }
            });

            if (response.ok) {
                const data = await response.json();
                console.log("AAA:", data);

                for (let c of data) {
                    // console.log(c);
                    events.push({
                        title: c.courseName,
                        start: new Date(c.dateTime),
                        end: new Date(new Date(c.dateTime).getTime() + c.duration * 60000),
                        desc: c.classroomDto.building + ", " + c.classroomDto.floor + ", " + c.classroomDto.number,
                        color: "lightblue",
                        isRequest: false,
                        classSession: c
                    });
                }
                // setClassesEvents(events);
                console.log("Events: ", events);
            }
        } catch (e) {
            console.log("Error during classes fetching:", e)
        }
    }

    const fetchAll = async () => {
        let events = [];
        await fetchClasses(events);
        await fetchRescheduleRequests(events);
        setClassesEvents(events);
    }

    useEffect(() => {
        if (userId) fetchAll();
    }, [userId])

    return (
        <ClassesContext.Provider value={{classesEvents, fetchAll}}>
            {children}
        </ClassesContext.Provider>
    )
}

export const useClasses = () => useContext(ClassesContext);