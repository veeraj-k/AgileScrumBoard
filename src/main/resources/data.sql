-- INSERT INTO column (LOCATION,TITLE) VALUES(1,'To-Do');
-- INSERT INTO column (LOCATION,TITLE) VALUES(2,'In-Progress');
-- INSERT INTO column (LOCATION,TITLE) VALUES(3,'In-Review');
-- INSERT INTO column (LOCATION,TITLE) VALUES(4,'Done');
--
--
-- -- -- tasks
-- --
-- -- -- Add tasks to To-Do column
-- -- INSERT INTO task (id, columnid, description, title) VALUES
-- --                                                          (1, 101, 'Fix login page CSS', 'Login page CSS bug'),
-- --                                                          (2, 101, 'Add validation to form', 'Form validation');
-- --
-- -- -- Add tasks to In-Progress Column
-- -- INSERT INTO task (id, columnid, description, title) VALUES
-- --                                                          (3, 102, 'Work on new profile page', 'New profile page'),
-- --                                                          (4, 102, 'Configure CI/CD pipeline', 'Configure pipeline');
-- --
-- -- -- Add tasks to In-Review Column
-- -- INSERT INTO task (id, columnid, description, title) VALUES
-- --                                                          (5, 103, 'Review order checkout flow', 'Checkout review'),
-- --                                                          (6, 103, 'Verify payment gateway', 'Payment verification');
-- --
-- -- -- Add tasks to Done Column
-- -- INSERT INTO task (id, columnid, description, title) VALUES
-- --                                                          (7, 104, 'Updated entities for user roles ', 'User roles update'),
-- --                                                          (8, 104, 'Fixed cart calculation bug', 'Cart bugfix done');
-- --
-- --
-- --
-- -- -- -- comments
-- --
-- -- INSERT INTO task_comment (taskid, description) VALUES (1, 'Align buttons with rest of website');
-- --
-- -- INSERT INTO task_comment (taskid, description)
-- -- VALUES (1, 'Use proper status codes in API responses');
-- --
-- -- -- More comments for Task #2
-- -- INSERT INTO task_comment (taskid, description)
-- -- VALUES (2, 'Client side validation needed as well');
-- --
-- -- INSERT INTO task_comment (taskid, description)
-- -- VALUES (2, 'Populate default values wherever applicable');
-- --
-- -- -- More comments for Task #3
-- -- INSERT INTO task_comment (taskid, description)
-- -- VALUES (3, 'Reuse address components from checkout page');
-- --
-- -- INSERT INTO task_comment (taskid, description)
-- -- VALUES (3, 'Allow users to update email and phone numbers');
-- --
-- -- -- Task #4 comments
-- -- INSERT INTO task_comment (taskid, description)
-- -- VALUES (4, 'Ensure proper security policies in CI/CD');
-- --
-- -- INSERT INTO task_comment (taskid, description)
-- -- VALUES (4, 'Setup automated tests for build validation');
-- --
-- -- -- Add comments for other tasks also...
-- --


insert into board (name,description) values ('Sprint','Spring session to fix mobile app');

-- Columns
-- INSERT INTO column (title, location) VALUES
--                                          ('To Do', 1),
--                                          ('In Progress', 2),
--                                          ('In Review', 3),
--                                          ('Done', 4);

-- Columns
INSERT INTO column (title, location,boardid)
SELECT 'To Do', 1, id FROM board WHERE name='Sprint'
UNION
SELECT 'In Progress', 2, id FROM board WHERE name='Sprint'
UNION
SELECT 'In Review', 3, id FROM board WHERE name='Sprint'
UNION
SELECT 'Done', 4, id FROM board WHERE name='Sprint';


-- -- To Do Tasks
-- INSERT INTO task (title, description, columnid) VALUES
--                                                     ('FixBrokenLink','Home page link broken',1),
--                                                     ('UpdateStyles','Align all buttons',1),
--                                                     ('EnableSSL','Configure HTTPS',1),
--                                                     ('AddCart','Design shopping cart',1),
--                                                     ('ProfilePage','Add edit profile form',1);
--
-- -- In Progress Tasks
-- INSERT INTO task (title, description, columnid)  VALUES
--                                                      ('ChartAPI','Integrate new chart library',2),
--                                                      ('Payments','Work on payment gateway',2),
--                                                      ('Testing','Write unit test cases for core logic',2),
--                                                      ('EmailAPI','Setup email API integration',2);
--
-- -- In Review Tasks
-- INSERT INTO task (title, description, columnid) VALUES
--                                                     ('ReviewStyle','Verify CSS styles',3),
--                                                     ('TestingComments','Provide feedback on test plan',3),
--                                                     ('ApproveChanges','Review PR changes to be merged',3);
--
-- -- Done Tasks
-- INSERT INTO task (title, description, columnid) VALUES
--                                                     ('Bugfix1234','Fixed order total calculation',4),
--                                                     ('UserLogin','Implemented JWT auth for login',4),
--                                                     ('DBIndexes','Added indexes on search columns',4);

-- -- Column - To Do
-- INSERT INTO task (title, description, columnid)
-- SELECT 'Improve Navbar','Make dropdowns responsive', id FROM column WHERE title = 'To Do';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Check alignment on mobile' FROM task WHERE title='Improve Navbar';
--
-- INSERT INTO task (title, description, columnid)
-- SELECT 'User Dashboard' , 'Add reports section', id FROM column WHERE title = 'To Do';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Include sales, orders reports' FROM task WHERE title='User Dashboard';
--
-- -- Column - In Progress
-- INSERT INTO task (title, description, columnid)
-- SELECT 'Payment Integrations', 'Research Stripe', id FROM column WHERE title = 'In Progress';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Consider 2FA requirements' FROM task WHERE title='Payment Integrations';
--
-- INSERT INTO task (title, description, columnid)
-- SELECT 'New API Endpoints', 'Add customer, order APIs', id FROM column WHERE title = 'In Progress';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Follow REST API conventions' FROM task WHERE title='New API Endpoints';
--
-- -- Column - In Review
-- INSERT INTO task (title, description, columnid)
-- SELECT 'Review Order Workflow', 'Validate status changes', id FROM column WHERE title = 'In Review';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Check edge cases for cancellations' FROM task WHERE title='Review Order Workflow';
--
-- -- Add more...
--
-- -- Column - To Do
-- INSERT INTO task (title, description, columnid)
-- SELECT 'Improve Onboarding', 'Guide new users', id FROM column WHERE title='To Do';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Highlight key website sections' FROM task WHERE title='Improve Onboarding';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Link to docs and help articles' FROM task WHERE title='Improve Onboarding';
--
-- -- Column - In Progress
-- INSERT INTO task (title, description, columnid)
-- SELECT 'New Dashboard Charts', 'Add sales metrics charts', id FROM column WHERE title='In Progress';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Show sales numbers for last 7 days' FROM task WHERE title='New Dashboard Charts';
--
-- -- Column - In Review
-- INSERT INTO task (title, description, columnid)
-- SELECT 'Review Transaction Logs', 'Check for errors', id FROM column WHERE title='In Review';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Document any troubleshooting steps' FROM task WHERE title='Review Transaction Logs';
--
-- -- Column - Done
-- INSERT INTO task (title, description, columnid)
-- SELECT 'Resolved Account Creation Issue', 'Fixed sign up bugs', id FROM column WHERE title='Done';
--
-- INSERT INTO task_comment (taskid, description)
-- SELECT id, 'Marked ticket 456 as fixed and closed' FROM task WHERE title='Resolved Account Creation Issue';


-- added enums
-- Column - To Do
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'Improve Navbar', 'Make dropdowns responsive', 'Story', 3, id FROM column WHERE title = 'To Do';
--
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'User Dashboard', 'Add reports section', 'Story', 5, id FROM column WHERE title = 'To Do';

-- Column - In Progress
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'Payment Integrations', 'Research Stripe', 'Spike', NULL, id FROM column WHERE title = 'In Progress';

INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'New API Endpoints', 'Add customer APIs', 'Story', 8, id FROM column WHERE title = 'In Progress';

-- Column - In Review
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'Review Order Workflow', 'Validate status changes', 'Bug', NULL, id FROM column WHERE title = 'In Review';

-- Column - Done
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'Resolved Account Creation Issue', 'Fixed sign up bugs', 'Bug', NULL, id FROM column WHERE title = 'Done';

-- Column - To Do
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'Improve Onboarding', 'Guide new users', 'Story', 3, id FROM column WHERE title='To Do';

INSERT INTO task_comment (taskid, description)
SELECT id, 'Highlight key website sections' FROM task WHERE title='Improve Onboarding';

INSERT INTO task_comment (taskid, description)
SELECT id, 'Link to docs and help articles' FROM task WHERE title='Improve Onboarding';

-- Column - In Progress
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'New Dashboard Charts', 'Add sales metrics charts', 'Story', 5, id FROM column WHERE title='In Progress';

INSERT INTO task_comment (taskid, description)
SELECT id, 'Show sales numbers for last 7 days' FROM task WHERE title='New Dashboard Charts';

-- Column - In Review
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'Review Transaction Logs', 'Check for errors', 'Bug', NULL, id FROM column WHERE title='In Review';

INSERT INTO task_comment (taskid, description)
SELECT id, 'Document any troubleshooting steps' FROM task WHERE title='Review Transaction Logs';

-- Column - Done
INSERT INTO task (title, description, type, story_points, columnid)
SELECT 'Resolved Account Creation Issue', 'Fixed sign up bugs', 'Bug', NULL, id FROM column WHERE title='Done';

INSERT INTO task_comment (taskid, description)
SELECT id, 'Marked ticket 456 as fixed and closed' FROM task WHERE title='Resolved Account Creation Issue';
