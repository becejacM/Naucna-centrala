package ftn.uns.ac.rs.naucnaCentrala.camunda.utils;

public class CamundaRestContrants {
	
	public static final String START_PROCESS = "/process-definition/key/%s/start";
    public static final String TASK = "/task";
    public static final String SUBMIT_FORM = "/task/%s/submit-form";
    public static final String FORM_VARIABLES = "/task/%s/form-variables";
    public static final String CREATE_USER = "/user/create";
    public static final String GET_VARIABLE = "/process-instance/%s/variables/%s";
    public static final String SET_VARIABLE = "/process-instance/%s/variables/%s/data";
    public static final String GET_TASK = "/task/%s";
    
    public static final String ENGINE_BASE_URL = "http://localhost:8088/rest";
    
    //ftn.uns.ac.rs.naucnaCentrala.camunda.tasks.RegistrationStoreDataTask

}
