import React, {useContext, useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {RefreshContext} from "../../../App.jsx";
import axios from "axios";
import {toast} from "react-toastify";

export const TaskStatus = (prop) => {

    const [taskColumn,setTaskColumn] = useState();
    const [columns,setColumns] = useState([]);

    const {teamid} = useParams();
    const {taskid} = useParams();
    const {refresh,setRefresh} = useContext(RefreshContext);
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    useEffect(() => {
        const getTaskColumn = async () => {

            const response = (await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/${taskid}/getColumn`));
            const jsonData = await response.json();
            await setTaskColumn(jsonData);
            console.log("TaskColumn :" ,jsonData);

            const response2 = (await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/plaincolumns`));
            const jsonData2 = await response2.json();
            await setColumns(jsonData2);
            console.log("All columns :" ,jsonData2);

        }
        getTaskColumn();
    }, []);
    const handleChangeStatus = async (e) => {
        console.log("column_id:", e.target.value);
        const updateresponse = await axios.put(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/movetask`, {
            id: taskid,
            column_id: e.target.value,
        });

        if(updateresponse.status == 200){
            toast.success("Task Status changed successfully");
        }
        else{
            toast.success(updateresponse.statusText);
        }
        setRefresh(prev=>prev+1);
    }
    return (
        <>
            <select className="select w-full max-w-xs text-lg font-semibold" onChange={handleChangeStatus}>
                {
                    Array.isArray(columns) && columns.map((col) => (
                        <option selected={taskColumn.id == col.id} value={col.id} key={col.id}>{col.title}</option>
                    ))
                }

            </select>
        </>
    )
}
