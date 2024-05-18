import React, {useContext, useEffect, useState} from "react";
import ReactQuill from "react-quill";
import 'react-quill/dist/quill.bubble.css';
import "react-quill/dist/quill.snow.css";
import axios from "axios";
import {RefreshContext} from "../../../App.jsx";
import {useParams} from "react-router-dom";
import {toast} from "react-toastify";


export const TaskDescription = (prop) => {
    const [isEditing, setIsEditing] = useState(false);
    const { refresh, setRefresh } = useContext(RefreshContext);
    const [description, setDescription] = useState('');
    const [isloading, setIsloading] = useState(true);
    const toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],        // toggled buttons
        ['blockquote', 'code-block'],
        ['link', 'image', 'formula'],
        [{ 'font': [] }],
        [{ 'list': 'ordered' }, { 'list': 'bullet' }, { 'list': 'check' }],
        [{ 'script': 'sub' }, { 'script': 'super' }],      // superscript/subscript
        [{ 'align': [] }],
    ];
    const {teamid} = useParams();
    const {taskid} = useParams();
    const module = {
        toolbar: toolbarOptions,
    };
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    useEffect(() => {
        fetchTaskData();
    }, [refresh]);

    const fetchTaskData = async () => {
        setIsloading(()=>true)
        try {
            const response = await axios.get(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/${taskid}`);
            setDescription(response.data.description);
            setIsloading(()=>false)
        } catch (error) {
            toast.error(error)
            console.error('Error fetching task data:', error);
        }
    };

    const handleDoubleClick = () => {
        setIsEditing(true);
    };


    const handleSave = async () => {
        if (description.length !== 0) {
            try {
                await axios.put(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/${taskid}/editDescription`, description , {
                    headers: {
                        'Content-Type': 'text/plain'
                    }
                });
                setRefresh(prev => prev + 1);
                setIsEditing(false);
            } catch (error) {
                console.error('Error updating description:', error);
            }
        }
    };

    return (
        <>
            <div className="mb-8">
                <h2 className="font-semibold text-lg mb-2">Description</h2>
                {
                    isloading ? <div className="skeleton h-32"></div> : (isEditing ? (
                            <div>
                                <ReactQuill
                                    theme={"snow"}
                                    id={"1"}
                                    value={description}
                                    onChange={setDescription}
                                    modules={module}
                                />
                                <button className="btn btn-primary" onClick={handleSave}>
                                    Save
                                </button>
                            </div>
                        ) : (
                            <div
                                className="ql-editor text-lg hover:cursor-text"
                                dangerouslySetInnerHTML={{ __html: description }}
                                onDoubleClick={handleDoubleClick}
                            />
                        ))
                }


            </div>
        </>
    );
};