package com.demo.service;

import com.demo.core.data.States;
import com.demo.core.data.Transitions;
import com.demo.model.SMModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SMService {

    private Map<Long, SMModel> models = new HashMap<>(Map.of(1L, new SMModel(1, 0, States.S2), 2L, new SMModel(2, 100, States.S4)));

    @Autowired
    private StateMachineFactory<States, Transitions> smFactory;

    public SMModel fetch(long id) {
        return models.get(id);
    }

    public SMModel save(SMModel model) {
        models.put(model.getId(), model);
        return model;
    }

    public SMModel applyEvent(Long id, Transitions event) {
        getStateMachine(id).sendEvent(event);
        return fetch(id);
    }

    private StateMachine<States, Transitions> getStateMachine(long id) {
        StateMachine<States, Transitions> stateMachine = smFactory.getStateMachine("sm:" + id);

        SMModel smModel = fetch(id);

        stateMachine.getExtendedState().getVariables().put("model", smModel);

        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(access -> {
                    access.resetStateMachine(new DefaultStateMachineContext<>(smModel.getState(), null, null, null));
                    access.addStateMachineInterceptor(new StateMachineInterceptorAdapter<States, Transitions>() {
                        public StateContext<States, Transitions> postTransition(StateContext<States, Transitions> stateContext) {
                            SMModel smModel = stateMachine.getExtendedState().get("model", SMModel.class);
                            smModel.setState(stateContext.getStateMachine().getState().getId());
                            save(smModel);
                            return stateContext;
                        }
                    });
                });

        stateMachine.start();
        return stateMachine;
    }
}
