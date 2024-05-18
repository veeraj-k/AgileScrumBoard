import {TaskHeader} from "./TaskHeader.jsx";
import {TaskDescription} from "./TaskDescription.jsx";
import {TaskComments} from "./TaskComments.jsx";
import {Link, useParams} from "react-router-dom";
import React, {useContext, useEffect, useState} from "react";
import {RefreshContext} from "../../../App.jsx";
import {TaskStatus} from "./TaskStatus.jsx";

export default function OpenTask() {
    const {taskid} = useParams();
    const {teamid} = useParams();
    const {sprintid} = useParams();
    const [task,setTask] = useState([]);
    const [sprint, setSprint] = useState([]);
    const [isloading, setIsloading] = useState(true);
    const {refresh, setRefresh} = useContext(RefreshContext);
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    useEffect(() => {
            const getTask = async () =>{
                setIsloading(()=>true)
                const response = (await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/${taskid}`));
                const jsonData = await response.json();
                await setTask(jsonData);
                console.log("Tasks :" ,jsonData);
                const response2 = (await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint`));
                const jsonData2 = await response2.json();
                setSprint(jsonData2)
                setIsloading(()=>false)
            };
            getTask();
    }, [refresh]);
    function formatDateTime(backendTimestamp) {
        const date = new Date(backendTimestamp);

        // Format the date
        const formattedDate = date.toLocaleDateString("en-US", {
            year: "numeric",
            month: "long",
            day: "numeric",
        });

        // Format the time
        const formattedTime = date.toLocaleTimeString("en-US", {
            hour: "numeric",
            minute: "numeric",
        });

        // Combine formatted date and time
        const formattedDateTime = `${formattedDate} at ${formattedTime}`;

        return formattedDateTime;
    }
    return (
        <>
            <div className="pl-2.5">
                <div className="text-sm breadcrumbs">
                    <ul>
                        <li><Link to="/">Home</Link></li>
                        <li><Link to="/">Teams</Link></li>
                        <li><Link to={`/teams/${teamid}/sprint`}>Sprints</Link></li>
                        <li><Link to={`/teams/${teamid}/sprint/${sprintid}`}>Board</Link></li>
                        <li>{taskid}</li>
                    </ul>
                </div>
                <br/>

            </div>
            <div className="flex max-w-7xl mx-auto my-8">
                <div className="flex flex-col w-3/5 pr-8">
                    <TaskHeader title={task.title} id={task.id}/>

                    <TaskDescription description={task.description} id={task.id}/>
                    <TaskComments/>
                </div>
                <div className="w-2/5">
                    <div className="flex justify-between items-center mb-6">

                        <TaskStatus id={task.id}/>

                    </div>
                    {
                        isloading ? <div className="skeleton rounded-md h-80"></div> :
                            <div className="bg-base-300 p-6 rounded-md">

                                <div className="space-y-4">
                                    <div>
                                        <h3 className="font-semibold">Details</h3>
                                        <div className="mt-2">
                                            <p className="text-sm text-gray-600">Assignee</p>
                                            <p className="text-sm">Manager</p>
                                        </div>
                                        <div className="mt-2">
                                            <p className="text-sm text-gray-600">Assignee To</p>
                                            <p className="text-sm">{task.user ? task.user.name : "None"}</p>
                                        </div>

                                        <div className="mt-2">
                                            <p className="text-sm text-gray-600">Sprint</p>
                                            <p className="text-sm">{sprint.name}</p>
                                        </div>
                                        <div className="mt-2">
                                            <p className="text-sm text-gray-600">Story point estimate</p>
                                            <p className="text-sm">{task.storyPoints}</p>
                                        </div>
                                        <div className="mt-2">
                                            <p className="text-sm text-gray-600">Created</p>
                                            <p className="text-sm">{formatDateTime(task.created)}</p>
                                        </div>
                                        <div className="mt-2">
                                            <p className="text-sm text-gray-600">Updated</p>
                                            <p className="text-sm">{formatDateTime(task.updated)}</p>
                                        </div>

                                    </div>

                                </div>
                            </div>
                    }


                </div>
            </div>
        </>
    )
}