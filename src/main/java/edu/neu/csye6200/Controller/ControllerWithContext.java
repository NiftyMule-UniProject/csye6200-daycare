package edu.neu.csye6200.Controller;

import edu.neu.csye6200.ApplicationContext;

public class ControllerWithContext
{
    public ControllerWithContext(ApplicationContext context)
    {
        this.context = context;
    }

    protected ApplicationContext context;
}
