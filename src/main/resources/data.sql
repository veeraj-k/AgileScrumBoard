--
insert into sprint (name,description,startdate) values ('Sprint','Spring session to fix mobile app','2024-02-16');

-- Columns
INSERT INTO bcolumn (title, location,sprintid,isvisible)
SELECT 'To Do', 4, id,true FROM sprint WHERE name='Sprint'
UNION
SELECT 'In Progress', 3, id,true FROM sprint WHERE name='Sprint'
UNION
SELECT 'In Review', 2, id,true FROM sprint WHERE name='Sprint'
UNION
SELECT 'Done', 1, id,true FROM sprint WHERE name='Sprint';

-- inserting users

insert into users_agile(name) values ('Veeraj'),('Yashwant'),('Vinay'),('Surya'),('Udit'),('Tejas');

--
-- Improve Navbar

--     adding users to tasks

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'Improve Navbar', 'Make dropdowns responsive', 'Story', 3, id, true, 1 FROM bcolumn WHERE title = 'To Do';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'User Dashboard', 'Add reports section', 'Story', 5, id, true, 2 FROM bcolumn WHERE title = 'To Do';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'Payment Integrations', 'Research Stripe', 'Spike', 0, id, true, 3 FROM bcolumn WHERE title = 'In Progress';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'New API Endpoints', 'Add customer APIs', 'Story', 8, id, true, 4 FROM bcolumn WHERE title = 'In Progress';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'Review Order Workflow', 'Validate status changes', 'Bug', 0, id, true, 5 FROM bcolumn WHERE title = 'In Review';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'Resolved Account Creation Issue', 'Fixed sign up bugs', 'Bug', 0, id, true, 6 FROM bcolumn WHERE title = 'Done';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'Improve Onboarding', 'Guide new users', 'Story', 3, id, true, 1 FROM bcolumn WHERE title='To Do';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'New Dashboard Charts', 'Add sales metrics charts', 'Story', 5, id, true, 2 FROM bcolumn WHERE title='In Progress';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'Review Transaction Logs', 'Check for errors', 'Bug', 0, id, true, 3 FROM bcolumn WHERE title='In Review';

INSERT INTO task (title, description, type, story_points, columnid, isvisible, userid)
SELECT 'Resolved Account Creation Issue', 'Fixed sign up bugs', 'Bug', 0, id, true, 4 FROM bcolumn WHERE title='Done';

-- Task Comments
INSERT INTO task_comment (taskid, description,user_id)
SELECT id, 'Highlight key website sections',1 FROM task WHERE title='Improve Onboarding';

INSERT INTO task_comment (taskid, description,user_id)
SELECT id, 'Link to docs and help articles',2 FROM task WHERE title='Improve Onboarding';

INSERT INTO task_comment (taskid, description,user_id)
SELECT id, 'Show sales numbers for last 7 days',3 FROM task WHERE title='New Dashboard Charts';

INSERT INTO task_comment (taskid, description,user_id)
SELECT id, 'Document any troubleshooting steps',1 FROM task WHERE title='Review Transaction Logs';

INSERT INTO task_comment (taskid, description,user_id)
SELECT id, 'Marked ticket 456 as fixed and closed',2 FROM task WHERE title='Resolved Account Creation Issue';



-- second try





