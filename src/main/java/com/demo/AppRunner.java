package com.demo;

import com.demo.core.data.States;
import com.demo.core.data.Transitions;
import com.demo.model.SMModel;
import com.demo.service.SMService;
import com.demo.sm.actions.SMActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Map;

import static com.demo.core.data.Transitions.T2;

@Service
@DependsOn("SMActions")
public class AppRunner {


    @Autowired
    private SMService smService;

    @PostConstruct
    public void init() {
        handleAndMakeTransition();
    }

    private void handleAndMakeTransition() {
        SMModel smModel = smService.fetch(1L);
        System.out.println(smModel.getState());
        smModel = smService.applyEvent(1L, T2);
        System.out.println(smModel.getState());
    }
}
