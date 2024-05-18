import React, {useContext, useEffect, useState} from "react";
import styled from "@emotion/styled";
import {DragDropContext, Draggable, Droppable} from "react-beautiful-dnd";
import {Grid, Box} from "@mui/material";
import Divider from "@mui/material/Divider";


import TaskCard from "./TaskCard.jsx";
import AddColumn from "./AddColumn.jsx";
import {Link, useLocation, useParams} from "react-router-dom";
import {RefreshContext} from "../../App.jsx";
import axios from "axios";
import {DeleteColumn} from "./DeleteColumn.jsx";
import CreateTaskModal from "./CreateTaskModal.jsx";
import { IoTimeOutline } from "react-icons/io5";
import {CompleteSprint} from "./CompleteSprint.jsx";
import {toast} from "react-toastify";

const Container = styled("div")(() => ({
    display: "flex",
    flexDirection: "row",
}));

const TaskList = styled("div")(() => ({
    minHeight: "100px",
    display: "flex",
    flexDirection: "column",
    minWidth: "341px",
    borderRadius: "5px",
    padding: "15px 15px",
    marginRight: "10px",
    position: "relative", // Add this
}));

const TaskColumnStyles = styled("div")(() => ({
    margin: "6px",
    display: "flex",
    width: "100%",
    minHeight: "80vh",
}));

const Title = styled("span")(() => ({
    fontWeight: "bold",
    fontSize: 16,
    marginBottom: "1.5px",
}));

const StickyHeader = styled("div")(() => ({
    position: "sticky", // Add this
    top: 0, // Add this
    zIndex: 1, // Add this
    backgroundColor: "inherit", // Add this
    paddingBottom: "8px", // Add this
}));

const ColumnContent = styled("div")(() => ({
    paddingTop: "8px", // Add this
}));

const ScrumBoard = () => {

    const [columns, setColumns] = useState([]);
    const [sprint, setSprint] = useState([]);
    const [days, setDays] = useState();
    const { refresh, setRefresh } = useContext(RefreshContext);
    const [loading,setLoading] = useState(true);
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    const {teamid} = useParams();
    const {sprintid} = useParams();

    useEffect(() =>{
        const fetchdata = async () =>{
            setLoading(true);
            const response = await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint/columns`);
            const  jsondata =  await response.json();
            console.log(jsondata);
            setColumns(jsondata);
            setLoading(false);
        };
        const fetchSprint = async () =>{
            const sprintresponse = await fetch(`${backendApiUrl}/api/teams/${teamid}/sprint`);
            const  sprintjsondata =  await sprintresponse.json();
            setSprint(sprintjsondata);
            const currentDate = new Date();
            const backendDate = new Date(sprintjsondata.enddate);
            const differenceInTime = backendDate.getTime() - currentDate.getTime();
            const differenceInDays = Math.ceil(differenceInTime / (1000 * 3600 * 24));
            setDays(differenceInDays);
        };
        fetchdata();
        fetchSprint();

    },[refresh]);

    const onDragEnd = async (result, columns, setColumns) => {
        if (!result.destination) return;
        const { source, destination } = result;

        if (source.droppableId !== destination.droppableId) {

            const sourceColumn = columns[source.droppableId];



            const destColumn = columns[destination.droppableId];
            const sourceItems = [...sourceColumn.tasks];
            const destItems = [...destColumn.tasks];
            const [removed] = sourceItems.splice(source.index, 1);
            console.log("removed");
            destItems.splice(destination.index, 0, removed);

            setColumns({
                ...columns,
                [source.droppableId]: {
                    ...sourceColumn,
                    tasks: sourceItems,
                },
                [destination.droppableId]: {
                    ...destColumn,
                    tasks: destItems,
                },
            });
            const response = await axios.put(`${backendApiUrl}/api/teams/${teamid}/sprint/columns/tasks/movetask`, {
                id: columns[source.droppableId].tasks[source.index].id,
                column_id: columns[destination.droppableId].id,
            });
            if (response.status==200){
                toast.success("Task Moved Successfully!");
            }else {
                toast.error(response.message);
            }
        } else {
            const column = columns[source.droppableId];
            const copiedItems = [...column.tasks];
            const [removed] = copiedItems.splice(source.index, 1);
            copiedItems.splice(destination.index, 0, removed);
            setColumns({
                ...columns,
                [source.droppableId]: {
                    ...column,
                    tasks: copiedItems,
                },
            });
        }
    };
    return (
        <div>

            <div className="text-sm breadcrumbs">
                <ul>
                    <li><a>Projects</a></li>
                    <li><Link to={"/"} >Teams</Link></li>
                    <li><Link to={`/teams/${teamid}/sprint`} >Sprint</Link></li>
                    <li>{sprint.name}</li>
                </ul>
            </div>
            <div className="flex justify-between text-xl rounded m-2 px-3">
                <div className={"text-2xl font-bold underline"}>{sprint.name}</div>

                <div className={"flex justify-center items-center  mx-2 gap-3 text-center"}>
                    <IoTimeOutline />
                    {days>=0? <div>{days} days remaining</div>: <div >{days *-1} days due</div>}
                    |
                    <CompleteSprint/>
                </div>

            </div>
            <div className={"flex justify-end pr-2"} >
                <div><CreateTaskModal/></div>

            </div>

            {
                loading? (
                    <div className="flex flex-row gap-4 w-full">
                        <div className="skeleton h-screen w-1/4"></div>
                        <div className="skeleton h-screen w-1/4"></div>
                        <div className="skeleton h-screen w-1/4"></div>
                        <div className="skeleton h-screen w-1/4"></div>
                    </div>

                ) : (
                    <div>
                        <div
                            className="overflow-x-auto overflow-y-auto max-h-[calc(100vh-80px)] scrollbar-thin scrollbar-thumb-rounded scrollbar-thumb-gray-300 hover:scrollbar-thumb-gray-400">


                            <DragDropContext
                                onDragEnd={(result) => {
                                    onDragEnd(result, columns, setColumns);
                                }}
                            >


                                <Container>
                                    <AddColumn setRefresh={setRefresh}/>
                                    <TaskColumnStyles>
                                        {Object.entries(columns).map(([columnId, column], index) => {
                                            return (

                                                <Droppable key={index} droppableId={String(columnId)}>
                                                    {(provided, snapshot) => (
                                                        <TaskList
                                                            className="bg-base-content "
                                                            ref={provided.innerRef}
                                                            {...provided.droppableProps}
                                                        >
                                                            <StickyHeader>
                                                                <Box sx={{width: "100%"}}>
                                                                    <Grid
                                                                        container
                                                                        rowSpacing={1}
                                                                        columnSpacing={{xs: 1, sm: 2, md: 3}}
                                                                    >
                                                                        <Grid item xs={10} key={index}>
                                                                            <Title
                                                                                className="text-base-100">{column.title}</Title>

                                                                        </Grid>
                                                                        <Grid
                                                                            item
                                                                            xs={2}
                                                                            // key={index}
                                                                            display="flex"
                                                                            alignContent="flex-end"
                                                                            justifyContent="flex-end"
                                                                        >
                                                                            {/* Add any additional elements you want in the header */}
                                                                            <DeleteColumn column={column}/>
                                                                        </Grid>
                                                                    </Grid>
                                                                </Box>
                                                            </StickyHeader>
                                                            <ColumnContent>
                                                                <Divider/>
                                                                {column.tasks.map((item, index) => (
                                                                    <TaskCard key={index} item={item} index={index}/>
                                                                ))}
                                                                {provided.placeholder}
                                                            </ColumnContent>
                                                        </TaskList>
                                                    )}
                                                </Droppable>
                                            );
                                        })}
                                    </TaskColumnStyles>

                                </Container>
                            </DragDropContext>
                        </div>
                    </div>
                )

            }


        </div>

    )
        ;
};

export default ScrumBoard;
