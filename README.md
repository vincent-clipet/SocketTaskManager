SocketTaskManager
=================


# Start
-------
java Client (host port)
java Server (port)


# Client Commands
-----------------
- HELP
- CREATE			(name)
- DELETE
- ASSIGN			(worker)
- STATUS			(status)
- DESC				(desc)
- GET_BY_ID			(id)
- GET_BY_NAME		(name)
- GET_BY_STATUS		(status)
- GET_BY_WORKER		(worker)
- GET_ALL


# Client Usage
--------------
* Launch program
* Enter host/port/login when asked
* Use one of the above commands
	(NB: 'active' commands (DELETE/ASSIGN/STATUS/DESC) require that you already have a selected task (with GET_BY_ID or GET_BY_NAME) 