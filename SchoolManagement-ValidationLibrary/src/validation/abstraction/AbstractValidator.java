/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validation.abstraction;

import validaton.rule.Rule;
import validation.builder.RuleBuilderImpl;
import validation.builder.RuleBuilder;
import java.util.ArrayList;
import java.util.List;
import validaton.rule.result.ResultInfo;

/**
 *
 * @author ivano
 */
public abstract class AbstractValidator {

    private List<String> errors = new ArrayList<>();
    private List<Rule> rules = new ArrayList<>();

    public RuleBuilder ruleFor(Object property) {
        return new RuleBuilderImpl(this, property);
    }

    public ResultInfo validate() {
        for (Rule rule : rules) {
            String error = rule.validate();
            if (error != null) {
                errors.add(error);
            }
        }

        ResultInfo resultInfo = new ResultInfo();

        if (!errors.isEmpty()) {
            resultInfo.setValid(false);
            resultInfo.setErrors(errors);
        } else {
            resultInfo.setValid(true);

        }
        
        return resultInfo;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules = rules;
    }

}
