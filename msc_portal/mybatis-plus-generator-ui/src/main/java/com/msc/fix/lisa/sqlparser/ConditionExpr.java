package com.msc.fix.lisa.sqlparser;

import cn.hutool.core.util.ReUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConditionExpr {

	private static final Pattern DYNAMIC_PARAM_PATTERN = Pattern.compile("#\\{(.*?)}");

	private String findPattern;

	private String logicOperator;

	private String leftExpr;

	private String rightExpr;

	private String operator;

	//如果operator是between，会存在middleOperator和endExpr
	private String middleOperator;

	private String endExpr;

	private List<String> paramNames;

	public int parseDynamicParams(String content) {
		if (paramNames == null) {
			paramNames = Lists.newArrayList();
		}
		Matcher m = DYNAMIC_PARAM_PATTERN.matcher(content);
		int index = 0;
		while (m.find()) {
			String group = m.group(index);
			paramNames.add(group.substring(2, group.length() - 1));
			index++;
		}
		return index;
	}

	public String getFindPattern() {
		StringBuilder pattern = new StringBuilder();
		if (!Strings.isNullOrEmpty(logicOperator)) {
			pattern.append("\\s+");
			pattern.append(logicOperator);
			pattern.append("\\s+");
		}
		pattern.append(leftExpr);
		pattern.append("\\s*");
		pattern.append(operator);
		pattern.append("\\s*");
		pattern.append(ReUtil.escape(getRightExpr().toString()));
		if (!Strings.isNullOrEmpty(middleOperator)) {
			pattern.append("\\s+");
			pattern.append(middleOperator);
			pattern.append("\\s+");
			pattern.append(ReUtil.escape(getEndExpr().toString()));
		}
		return pattern.toString();
	}

	public static boolean isDynamicParam(String content) {
		return DYNAMIC_PARAM_PATTERN.matcher(content).find();
	}

	public void setFindPattern(String findPattern) {
		this.findPattern = findPattern;
	}

	public String getLogicOperator() {
		return logicOperator;
	}

	public void setLogicOperator(String logicOperator) {
		this.logicOperator = logicOperator;
	}

	public String getLeftExpr() {
		return leftExpr;
	}

	public void setLeftExpr(String leftExpr) {
		this.leftExpr = leftExpr;
	}

	public String getRightExpr() {
		return rightExpr;
	}

	public void setRightExpr(String rightExpr) {
		this.rightExpr = rightExpr;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMiddleOperator() {
		return middleOperator;
	}

	public void setMiddleOperator(String middleOperator) {
		this.middleOperator = middleOperator;
	}

	public String getEndExpr() {
		return endExpr;
	}

	public void setEndExpr(String endExpr) {
		this.endExpr = endExpr;
	}

	public List<String> getParamNames() {
		return paramNames;
	}

	public void setParamNames(List<String> paramNames) {
		this.paramNames = paramNames;
	}

	@Override
	public String toString() {
		return "ConditionExpr{" +
				"findPattern='" + findPattern + '\'' +
				", logicOperator='" + logicOperator + '\'' +
				", leftExpr='" + leftExpr + '\'' +
				", rightExpr='" + rightExpr + '\'' +
				", operator='" + operator + '\'' +
				", middleOperator='" + middleOperator + '\'' +
				", endExpr='" + endExpr + '\'' +
				", paramNames=" + paramNames +
				'}';
	}
}
