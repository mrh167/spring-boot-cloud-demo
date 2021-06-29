package com.msc.fix.lisa.strategy;

import com.baomidou.mybatisplus.generator.config.ConstVal;

public class ServiceStrategy {

	/**
	 * 自定义继承的Service类全称，带包名
	 */
	private String superServiceClass = ConstVal.SUPER_SERVICE_CLASS;

	public String getSuperServiceClass() {
		return superServiceClass;
	}

	public void setSuperServiceClass(String superServiceClass) {
		this.superServiceClass = superServiceClass;
	}
}
