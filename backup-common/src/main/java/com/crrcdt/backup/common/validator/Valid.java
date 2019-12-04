package com.crrcdt.backup.common.validator;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.baidu.unbiz.fluentvalidator.Validator;
import com.crrcdt.backup.common.base.BaseResult;
import com.crrcdt.backup.common.constant.ResultConstant;

import java.util.Collection;
import java.util.List;

/**
 * 用于简化Fluent-validator封装
 *
 * @author lyh
 * @date 2019年11月1日11:10:14
 */
public class Valid {

    private FluentValidator fluentValidator = null;
    private ComplexResult result = null;

    /**
     * Instantiates a new Valid.
     * OR you can use static method Valid.init()
     */
    public Valid() {
    }

    /**
     * static constructor
     *
     * @return the valid
     */
    public static Valid valid() {
        return new Valid();
    }

    /**
     * On Valid.
     *
     * @param <T> the type parameter
     * @param t   the t
     * @param v   the v
     * @return the valid
     */
    public <T> Valid on(T t, Validator<T> v) {
        if (this.fluentValidator == null) {
            this.fluentValidator = FluentValidator.checkAll();
        }
        this.fluentValidator.on(t, v);
        this.result = null;
        return this;
    }

    /**
     * On each Valid.
     *
     * @param <T> the type parameter
     * @param t   the t
     * @param v   the v
     * @return the vali
     */
    public <T> Valid onEach(T[] t, Validator<T> v) {
        if (this.fluentValidator == null) {
            this.fluentValidator = FluentValidator.checkAll();
        }
        this.fluentValidator.onEach(t, v);
        this.result = null;
        return this;
    }

    /**
     * On each Valid.
     *
     * @param <T> the type parameter
     * @param t   the t
     * @param v   the v
     * @return the vali
     */
    public <T> Valid onEach(Collection<T> t, final Validator<T> v) {
        if (this.fluentValidator == null) {
            this.fluentValidator = FluentValidator.checkAll();
        }
        this.fluentValidator.onEach(t, v);
        this.result = null;
        return this;
    }

    /**
     * Is success boolean. 执行验证实例
     *
     * @return the boolean
     */
    public boolean isSuccess() {
        if (this.result == null) {
            this.result = this.fluentValidator
                    .doValidate()
                    .result(ResultCollectors.toComplex());
        }
        return this.result.isSuccess();
    }

    /**
     * Is error boolean.
     *
     * @return the boolean
     */
    public boolean isError() {
        return !this.isSuccess();
    }

    /**
     * Error info base result.
     *
     * @return the base result
     */
    public BaseResult errorInfo() {
        return new BaseResult(BaseResult.VALIDATOR, ResultConstant.VALIDATOR.getMessage(), this.result.getErrors());
    }

    /**
     * Gets errors.
     *
     * @return the errors
     */
    public List getErrors() {
        return this.result.getErrors();
    }


}
