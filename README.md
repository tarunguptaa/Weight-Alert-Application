# egen-be-challenge
Code submission against Coding Challenge BE-Level 2

#Metric APIs
	This will consume the data from Emulator and save it in mongoDb	
	~localhost:8080/metric/create

	This will expose all the saved data from mongoDb in metric collection
	~localhost:8080/metric/read
	
	This will expose the saved data from mongoDb	between a specific timestamp in millis in metric collection
	~localhost:8080/metric/readByTimeRange/{startTimeStamp}/{endTimeStamp}

#Alert APIs
	This will expose all the saved data from mongoDb in alert collection
	~localhost:8080/alert/read
	
	This will expose the saved data from mongoDb	between a specific timestamp in millis in alert collection
	~localhost:8080/alert/readByTimeRange/{startTimeStamp}/{endTimeStamp}
