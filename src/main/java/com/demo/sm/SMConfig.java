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

import static com.demo.core.data.States.*;
import static com.demo.core.data.Transitions.T1;
import static com.demo.core.data.Transitions.T2;

@Configuration
public class SMConfig {

    @Autowired
    SMActions actions;

    @Configuration
    @EnableStateMachineFactory
    public class Config extends EnumStateMachineConfigurerAdapter<States, Transitions> {

        @Override
        public void configure(StateMachineStateConfigurer<States, Transitions> states)
                throws Exception {
            states
                    .withStates()
                    .initial(S1)
                    .states(EnumSet.allOf(States.class))
                    .choice(S3);
        }

        @Override
        public void configure(StateMachineTransitionConfigurer<States, Transitions> transitions)
                throws Exception {
            transitions
                    .withExternal()
                    .source(S1)
                    .target(S2)
                    .event(T1)
                    .action((s) -> actions.onTransitionT1(s), (s) -> actions.onError(s))
                    .and()
                    .withExternal()
                    .source(S2)
                    .target(S3)
                    .event(T2)
                    .and()
                    .withChoice()
                    .source(S3)
                    .first(PS3_1, actions.onTransitionT3_X("1"))
                    .then(PS3_2, actions.onTransitionT3_X("2"))
                    .then(PS3_3, actions.onTransitionT3_X("3"))
                    .last(PS3_4);

        }
    }
}
