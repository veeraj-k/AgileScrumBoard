import React, {useContext, useEffect, useState} from "react";
import {RefreshContext} from "../../../App.jsx";
import axios from "axios";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";

export const TaskHeader = (prop) => {
    const [isEditing, setIsEditing] = useState(false);
    const { refresh, setRefresh } = useContext(RefreshContext);
    const [title, setTitle] = useState('');
    const [prevTitle, setPrevTitle] = useState('');
    const [isloading, setIsloading] = useState(true);
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    const {teamid} =useParams();
    const {taskid} = useParams();
    useEffect(() => {
        fetchTaskData();
    }, [refresh]);

    const fetchTaskData = async () => {
        setIsloading(()=>true);
        try {
            const response = await axios.get(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/${taskid}`);
            setTitle(response.data.title);
            setPrevTitle(response.data.title);
            setIsloading(()=>false)
        } catch (error) {
            toast.error(error)
            console.error('Error fetching task data:', error);
        }
    };

    const handleDoubleClick = () => {
        setIsEditing(true);
    };

    const handleCancel = () => {
        setTitle(prevTitle);
        setIsEditing(false);
    };

    const handleInputChange = (e) => {
        setTitle(e.target.value);
    };

    const handleSave = async () => {
        if (title.length !== 0) {
            try {
                await axios.put(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/${taskid}/editTitle`, title, {
                    headers: {
                        'Content-Type': 'text/plain '
                    }
                });
                setRefresh(prev => prev + 1);
                setPrevTitle(title);
                setIsEditing(false);
            } catch (error) {
                console.error('Error updating title:', error);
            }
        }
    };

    return (
        <>
            {
                isloading ? <div className="skeleton  h-8 rounded-md"></div>:(isEditing ? (
                        <div>
                            <div className="flex items-center">
                                <input
                                    type="text"
                                    value={title}
                                    onChange={handleInputChange}
                                    className="border border-gray-300 rounded-l-md px-3 py-2 text-2xl font-bold mr-2 w-full"
                                />
                                <button onClick={handleSave} className="btn btn-outline btn-success">
                                    ✓
                                </button>
                                <button onClick={handleCancel} className="btn btn-outline btn-error">
                                    X
                                </button>
                            </div>
                        </div>
                    ) : (
                        <h1 className="text-2xl font-bold mb-4 hover:cursor-text" onDoubleClick={handleDoubleClick}>
                            {title}
                        </h1>
                    ))
            }

        </>
    );
};