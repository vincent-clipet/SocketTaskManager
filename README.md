SocketTaskManager
=================


# Start
-------
java Client (host port)
java Server (port)


# Client Commands
-----------------
- HELP
- CREATE		(name)
- DELETE
- ASSIGN		(worker)
- STATUS		(status)
- DESC			(desc)
- GET_ID		(id)
- GET_NAME		(name)
- GET_STATUS	(status)
- GET_WORKER	(worker)
- GET_ALL


# Client Usage
--------------
* Launch program
* Enter host/port/login when asked
* Use one of the above commands
	(NB: 'active' commands (DELETE/ASSIGN/STATUS/DESC) require that you already have a selected task (with GET_ID or GET_NAME)
	(NB: 'status' value must be in 'DONE/FREE/ASSIGNED') 