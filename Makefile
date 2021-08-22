Test.class: Test.java AEP.jar
	javac Test.java

AEP.jar: edu/txstate/cs/AndroidEnergyProfiler.java
	javac edu/txstate/cs/AndroidProperties.java
	javac edu/txstate/cs/SimulatedBatteryManager.java
	javac edu/txstate/cs/AndroidEnergyProfiler.java
	jar cf AEP.jar edu/txstate/cs/*.class

clean:
	-@rm *.class
	-@rm edu/txstate/cs/*.class
	-@rm AEP.jar
