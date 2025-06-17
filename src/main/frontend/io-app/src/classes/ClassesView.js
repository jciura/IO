import {useEffect, useState} from "react";
import {Calendar, Views, momentLocalizer} from "react-big-calendar"
import moment from "moment"
import ClassView from "./ClassView";

function ClassesView() {
    const userFromLocalStorage = JSON.parse(localStorage.getItem("USER")) ?? null;
    const userId = userFromLocalStorage ? userFromLocalStorage.id : null;
    const [showClasses, setShowClasses] = useState(false);
    const [classes, setClasses] = useState([]);
    const [classesEvents, setClassesEvents] = useState([]);
    const [currentCourseName, setCurrentCourseName] = useState("");
    const [rescheduleRequests, setRescheduleRequests] = useState([]);
    const [completedRescheduleRequests, setCompletedRescheduleRequests] = useState([]);

    const [currentDate, setCurrentDate] = useState(new Date());
    const [currentView, setCurrentView] = useState(Views.WORK_WEEK);

    const localizer = momentLocalizer(moment);

    useEffect(() => {
        let events = [];

        const fetchRescheduleRequests = async () => {
            try {
                const response = await fetch(`http://localhost:8080/reschedule/${userFromLocalStorage.id}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    console.log("Requests: ", data);
                    setRescheduleRequests(data);

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

        const fetchCompletedRescheduleRequests = async () => {
            try {
                const response = await fetch(`http://localhost:8080/reschedule/completed/${userFromLocalStorage.id}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });

                if (response.ok) {
                    const data = await response.json();
                    console.log("Requests: ", data);
                    setCompletedRescheduleRequests(data);
                }
            } catch (error) {
                console.log("Error during completed reschedule requests fetching: ", error);
            }
        }

        const fetchClasses = async () => {
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
                    setClasses(data);

                    for (let c of data) {
                        // console.log(c);
                        events.push({
                            title: c.courseName,
                            start: new Date(c.dateTime),
                            end: new Date(new Date(c.dateTime).getTime() + c.duration * 60000),
                            desc: c.classroomDto.building + ", " + c.classroomDto.floor + ", " + c.classroomDto.number,
                            color: "blue",
                            isRequest: false,
                            classSession: c
                        });
                    }
                    setClassesEvents(events);
                    console.log("Events: ", events);
                }
            } catch (e) {
                console.log("Error during classes fetching:", e)
            }
        }
        fetchClasses();
        fetchRescheduleRequests();
        fetchCompletedRescheduleRequests();
        // setClassesEvents(events);
    }, [userId]);

    // async function showClassesWithinCourse(courseId, courseName) {
    //     try {
    //         const response = await fetch(`http://localhost:8080/classes/course_id/${courseId}`, {
    //             method: "GET",
    //             headers: {
    //                 "Content-Type": "application/json"
    //             }
    //         });
    //
    //         if (response.ok) {
    //             const data = await response.json();
    //             setClasses(data);
    //             console.log("Classes: ", data);
    //             setShowClasses(true);
    //             setCurrentCourseName(courseName);
    //         } else {
    //             console.log("Wrong course id!");
    //             setShowClasses(false);
    //         }
    //     } catch (error) {
    //         console.log("Error while loading course's classes: ", error);
    //     }
    // }

    return (
        <div className="p-4">
            <h1>Plan zajęć</h1>
            <hr></hr>
            <Calendar
                localizer={localizer}
                events={classesEvents}
                style={{height: 700}}
                date={currentDate}
                onNavigate={date => setCurrentDate(date)}
                views={[Views.DAY, Views.WORK_WEEK, Views.MONTH]}
                view={currentView}
                onView={view => setCurrentView(view)}
                messages={{today: "Aktualny", next: "Następny", previous: "Poprzedni", work_week: "Widok tygodnia", month: "Widok miesiąca", day: "Widok dnia"}}
                min={new Date(2025, 0, 1, 8, 0)}
                max={new Date(2025, 0, 1, 20, 0)}
                popup={true}
                eventPropGetter={(event) => {
                    return {
                        style: {backgroundColor: event.color}
                    }
                }}
                components={{event: ClassView}}
            />
        </div>
    )
}

export default ClassesView;