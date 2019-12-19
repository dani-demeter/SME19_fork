# SME19_TestDescriberProject
UZH Software Maintenance and Evolution. Test Code summarizing tool Test Describer, improvements and extensions. 

# Setup for Jenkins pipeline deployment; automatically run TD on a GitHub project
1) Set up Jenkins running in localhost
2) Install and Run Docker
3) Install a JDK in Jenkins in *Global Tool Configuration*
4) Create *Pipeline* in Jenkins
    a) Check *GitHub Project* checkbox
      i) Enter url to GitHub repo as *Project URL*
    b) Check *Pipeline Script from SCM*
      i) As *SCM*, select *Git*
      ii) Enter the url to the GitHub repo as *Repository URL*
      iii) Add GitHub credentials 
      iv) As *Script Path*, select Jenkinsfile
5) Create a Jenkinsfile in your project
    a) As a tool, it needs to reference the JDK downloaded earlier.
    b) First, it needs to clone this repository, or update its instance, if it already has.
    c) Then, it needs to run TD-Test.jar from the cloned repository with a text file as an input.
    d) This text file should include a path per line
      i) The first line needs to be the absolute path to the src folder
      ii) The second line needs to be the absolute path to the bin folder
      iii) Every pair of lines thereafter needs to be the additional paths to the class being tested, and the test class.
