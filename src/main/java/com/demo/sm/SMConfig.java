package com.demo.sm;

import com.demo.core.data.States;
import com.demo.core.data.Transitions;
import com.demo.sm.actions.SMActions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineModelConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.config.model.StateMachineModelFactory;

import java.util.EnumSet;

@Configuration
public class SMConfig {

    @Autowired
    SMActions actions;

    @Configuration
    @EnableStateMachine
    public class Config extends EnumStateMachineConfigurerAdapter<States, Transitions> {

        @Override
        public void configure(StateMachineStateConfigurer<States, Transitions> states)
                throws Exception {
            states
                    .withStates()
                    .initial(States.S1)
                    .states(EnumSet.allOf(States.class));
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Transitions> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source(States.S1)
                    .target(States.S2)
                    .event(Transitions.T1)
                    .action((s)->actions.onTransitionT1(s), (s) -> actions.onError(s))
                    .and()
                    .withExternal()
                    .source(States.S2)
                    .target(States.S3)
                    .event(Transitions.T2)
                    .and()
                    .withExternal()
                    .source(States.S3)
                    .target(States.S4)
                    .event(Transitions.T3);
        }
    }
}
