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


# A few explanations
--------------------
* "active" commands (DELETE/ASSIGN/STATUS/DESC) require that you already have at least one selected task (with one of the 'GET_xxx' commands)
* 'status' value must be in 'DONE/FREE/ASSIGNED'
* 'GET_xxx' commands act as selectors. They store the last set of results as your current selection. For example, 'GET_STATUS DONE' followed by 'DELETE' will delete all finished tasks


# Example script
----------------
netcat [host] [port] < test_script.txt
