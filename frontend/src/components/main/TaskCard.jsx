import * as React from "react";
import {
    Box,
    Card,
    CardHeader,
    Grid,
    Chip,
    Badge,
    Typography,
    CardContent,
    CardActions,
    Avatar,
} from "@mui/material";
import { Draggable } from "react-beautiful-dnd";
import { styled } from "@mui/system";
import {Link, useParams} from "react-router-dom";
import Divider from "@mui/material/Divider";



const Title = styled("div")(() => ({
    marginBottom: "1.5px",
    // color: "#ff0000",
}));

const SubTitle = styled("span")(() => ({
    marginBottom: "1.5px",
    color: "#111111",
    fontWeight: "bold",
}));

const Heading = styled("div")(({ theme }) => ({
    fontWeight: "bold",
    fontSize: "16px",
}));

const badgeClasses = {
    Story: "badge-success",
    Bug: "badge-error",
    Improvement: "badge-warning",
    Spike: "badge-secondary",
    Incident: "badge-neutral",
};



const TaskCard = ({ item, index }) => {
    const {teamid} = useParams();
    const {sprintid} = useParams();
    const backendApiUrl = import.meta.env.VITE_BACKEND_API_URL;
    return (

            <Draggable key={item.id} draggableId={String(item.id)} index={index}>
                {(provided) => (
                    <div
                        ref={provided.innerRef}
                        {...provided.draggableProps}
                        {...provided.dragHandleProps}
                    >
                        <Card sx={{ minWidth: 275, m: "8px 1px" ,maxWidth:430}}>
                            <CardHeader
                                avatar={
                                    <Avatar sx={{ bgcolor: "#2C5CC9" }} aria-label="recipe">
                                        {item.user.name.charAt(0).toUpperCase()}
                                    </Avatar>
                                }
                                // action={rightIconAction}
                            />
                            <CardContent sx={{p: "0 16px"}}>
                                <Heading>{item.user.name}</Heading>


                                <Typography
                                    sx={{fontSize: 19}}
                                    color="text.secondary"
                                    gutterBottom
                                >
                                    <div >
                                        <b>{item.title}</b>
                                    </div>


                                </Typography>

                                <Box sx={{flexGrow: 1, color: "#333333", m: "20px 0 0"}}>
                                    <Grid
                                        container
                                        spacing={{xs: 2, md: 3}}
                                        columns={{xs: 4, sm: 8, md: 12}}
                                    >
                                        {/*<Grid item xs={2} sm={4} md={4} key={index}>*/}
                                        <Grid item xs={2} sm={4} md={4} >
                                            <Title  className="base-100">Assignee</Title>
                                            <SubTitle>{"Manager"}</SubTitle>

                                        </Grid>
                                        {/*<Grid item xs={2} sm={4} md={4} key={index}>*/}
                                        <Grid item xs={2} sm={4} md={4} >
                                            <Title className="base-100">Type</Title>
                                            <SubTitle><div className={`badge ${badgeClasses[item.type]}`}>
                                                {item.type}
                                            </div></SubTitle>

                                        </Grid>
                                        {/*<Grid item xs={2} sm={4} md={4} key={index}>*/}
                                        <Grid item xs={2} sm={4} md={4} >
                                            <Title className="base-100">Points</Title>
                                            <SubTitle>{item.storyPoints}</SubTitle>
                                        </Grid>
                                    </Grid>
                                </Box>
                            </CardContent>
                            <Divider component="li" />
                            <CardActions>
                                <div className="flex justify-between container ">
                                    <div style={{ fontSize: "12px" }}>
                                        <button className="btn btn-primary btn-xs"><Link to={`/teams/${teamid}/sprint/${sprintid}/task/${item.id}`}>Open Task</Link></button>
                                    </div>
                                    <div
                                        style={{ fontSize: "16px" }}
                                        className="text-black text-opacity-50"
                                    >
                                        #{item.id}
                                    </div>
                                </div>
                            </CardActions>
                        </Card>
                    </div>
                )}
            </Draggable>
    );
};
export default TaskCard;