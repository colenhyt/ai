package box.site.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WebsiteExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public WebsiteExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andSiteidIsNull() {
            addCriterion("siteid is null");
            return (Criteria) this;
        }

        public Criteria andSiteidIsNotNull() {
            addCriterion("siteid is not null");
            return (Criteria) this;
        }

        public Criteria andSiteidEqualTo(Integer value) {
            addCriterion("siteid =", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidNotEqualTo(Integer value) {
            addCriterion("siteid <>", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidGreaterThan(Integer value) {
            addCriterion("siteid >", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidGreaterThanOrEqualTo(Integer value) {
            addCriterion("siteid >=", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidLessThan(Integer value) {
            addCriterion("siteid <", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidLessThanOrEqualTo(Integer value) {
            addCriterion("siteid <=", value, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidIn(List<Integer> values) {
            addCriterion("siteid in", values, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidNotIn(List<Integer> values) {
            addCriterion("siteid not in", values, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidBetween(Integer value1, Integer value2) {
            addCriterion("siteid between", value1, value2, "siteid");
            return (Criteria) this;
        }

        public Criteria andSiteidNotBetween(Integer value1, Integer value2) {
            addCriterion("siteid not between", value1, value2, "siteid");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andUrlIsNull() {
            addCriterion("url is null");
            return (Criteria) this;
        }

        public Criteria andUrlIsNotNull() {
            addCriterion("url is not null");
            return (Criteria) this;
        }

        public Criteria andUrlEqualTo(String value) {
            addCriterion("url =", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotEqualTo(String value) {
            addCriterion("url <>", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThan(String value) {
            addCriterion("url >", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlGreaterThanOrEqualTo(String value) {
            addCriterion("url >=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThan(String value) {
            addCriterion("url <", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLessThanOrEqualTo(String value) {
            addCriterion("url <=", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlLike(String value) {
            addCriterion("url like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotLike(String value) {
            addCriterion("url not like", value, "url");
            return (Criteria) this;
        }

        public Criteria andUrlIn(List<String> values) {
            addCriterion("url in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotIn(List<String> values) {
            addCriterion("url not in", values, "url");
            return (Criteria) this;
        }

        public Criteria andUrlBetween(String value1, String value2) {
            addCriterion("url between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andUrlNotBetween(String value1, String value2) {
            addCriterion("url not between", value1, value2, "url");
            return (Criteria) this;
        }

        public Criteria andBaiduurlIsNull() {
            addCriterion("baiduurl is null");
            return (Criteria) this;
        }

        public Criteria andBaiduurlIsNotNull() {
            addCriterion("baiduurl is not null");
            return (Criteria) this;
        }

        public Criteria andBaiduurlEqualTo(String value) {
            addCriterion("baiduurl =", value, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlNotEqualTo(String value) {
            addCriterion("baiduurl <>", value, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlGreaterThan(String value) {
            addCriterion("baiduurl >", value, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlGreaterThanOrEqualTo(String value) {
            addCriterion("baiduurl >=", value, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlLessThan(String value) {
            addCriterion("baiduurl <", value, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlLessThanOrEqualTo(String value) {
            addCriterion("baiduurl <=", value, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlLike(String value) {
            addCriterion("baiduurl like", value, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlNotLike(String value) {
            addCriterion("baiduurl not like", value, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlIn(List<String> values) {
            addCriterion("baiduurl in", values, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlNotIn(List<String> values) {
            addCriterion("baiduurl not in", values, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlBetween(String value1, String value2) {
            addCriterion("baiduurl between", value1, value2, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andBaiduurlNotBetween(String value1, String value2) {
            addCriterion("baiduurl not between", value1, value2, "baiduurl");
            return (Criteria) this;
        }

        public Criteria andCdescIsNull() {
            addCriterion("cdesc is null");
            return (Criteria) this;
        }

        public Criteria andCdescIsNotNull() {
            addCriterion("cdesc is not null");
            return (Criteria) this;
        }

        public Criteria andCdescEqualTo(String value) {
            addCriterion("cdesc =", value, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescNotEqualTo(String value) {
            addCriterion("cdesc <>", value, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescGreaterThan(String value) {
            addCriterion("cdesc >", value, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescGreaterThanOrEqualTo(String value) {
            addCriterion("cdesc >=", value, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescLessThan(String value) {
            addCriterion("cdesc <", value, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescLessThanOrEqualTo(String value) {
            addCriterion("cdesc <=", value, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescLike(String value) {
            addCriterion("cdesc like", value, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescNotLike(String value) {
            addCriterion("cdesc not like", value, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescIn(List<String> values) {
            addCriterion("cdesc in", values, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescNotIn(List<String> values) {
            addCriterion("cdesc not in", values, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescBetween(String value1, String value2) {
            addCriterion("cdesc between", value1, value2, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCdescNotBetween(String value1, String value2) {
            addCriterion("cdesc not between", value1, value2, "cdesc");
            return (Criteria) this;
        }

        public Criteria andCtitleIsNull() {
            addCriterion("ctitle is null");
            return (Criteria) this;
        }

        public Criteria andCtitleIsNotNull() {
            addCriterion("ctitle is not null");
            return (Criteria) this;
        }

        public Criteria andCtitleEqualTo(String value) {
            addCriterion("ctitle =", value, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleNotEqualTo(String value) {
            addCriterion("ctitle <>", value, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleGreaterThan(String value) {
            addCriterion("ctitle >", value, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleGreaterThanOrEqualTo(String value) {
            addCriterion("ctitle >=", value, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleLessThan(String value) {
            addCriterion("ctitle <", value, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleLessThanOrEqualTo(String value) {
            addCriterion("ctitle <=", value, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleLike(String value) {
            addCriterion("ctitle like", value, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleNotLike(String value) {
            addCriterion("ctitle not like", value, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleIn(List<String> values) {
            addCriterion("ctitle in", values, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleNotIn(List<String> values) {
            addCriterion("ctitle not in", values, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleBetween(String value1, String value2) {
            addCriterion("ctitle between", value1, value2, "ctitle");
            return (Criteria) this;
        }

        public Criteria andCtitleNotBetween(String value1, String value2) {
            addCriterion("ctitle not between", value1, value2, "ctitle");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNull() {
            addCriterion("keywords is null");
            return (Criteria) this;
        }

        public Criteria andKeywordsIsNotNull() {
            addCriterion("keywords is not null");
            return (Criteria) this;
        }

        public Criteria andKeywordsEqualTo(String value) {
            addCriterion("keywords =", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotEqualTo(String value) {
            addCriterion("keywords <>", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThan(String value) {
            addCriterion("keywords >", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsGreaterThanOrEqualTo(String value) {
            addCriterion("keywords >=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThan(String value) {
            addCriterion("keywords <", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLessThanOrEqualTo(String value) {
            addCriterion("keywords <=", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsLike(String value) {
            addCriterion("keywords like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotLike(String value) {
            addCriterion("keywords not like", value, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsIn(List<String> values) {
            addCriterion("keywords in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotIn(List<String> values) {
            addCriterion("keywords not in", values, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsBetween(String value1, String value2) {
            addCriterion("keywords between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andKeywordsNotBetween(String value1, String value2) {
            addCriterion("keywords not between", value1, value2, "keywords");
            return (Criteria) this;
        }

        public Criteria andBdrankIsNull() {
            addCriterion("bdrank is null");
            return (Criteria) this;
        }

        public Criteria andBdrankIsNotNull() {
            addCriterion("bdrank is not null");
            return (Criteria) this;
        }

        public Criteria andBdrankEqualTo(Integer value) {
            addCriterion("bdrank =", value, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankNotEqualTo(Integer value) {
            addCriterion("bdrank <>", value, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankGreaterThan(Integer value) {
            addCriterion("bdrank >", value, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankGreaterThanOrEqualTo(Integer value) {
            addCriterion("bdrank >=", value, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankLessThan(Integer value) {
            addCriterion("bdrank <", value, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankLessThanOrEqualTo(Integer value) {
            addCriterion("bdrank <=", value, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankIn(List<Integer> values) {
            addCriterion("bdrank in", values, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankNotIn(List<Integer> values) {
            addCriterion("bdrank not in", values, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankBetween(Integer value1, Integer value2) {
            addCriterion("bdrank between", value1, value2, "bdrank");
            return (Criteria) this;
        }

        public Criteria andBdrankNotBetween(Integer value1, Integer value2) {
            addCriterion("bdrank not between", value1, value2, "bdrank");
            return (Criteria) this;
        }

        public Criteria andAlexaIsNull() {
            addCriterion("alexa is null");
            return (Criteria) this;
        }

        public Criteria andAlexaIsNotNull() {
            addCriterion("alexa is not null");
            return (Criteria) this;
        }

        public Criteria andAlexaEqualTo(Integer value) {
            addCriterion("alexa =", value, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaNotEqualTo(Integer value) {
            addCriterion("alexa <>", value, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaGreaterThan(Integer value) {
            addCriterion("alexa >", value, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaGreaterThanOrEqualTo(Integer value) {
            addCriterion("alexa >=", value, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaLessThan(Integer value) {
            addCriterion("alexa <", value, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaLessThanOrEqualTo(Integer value) {
            addCriterion("alexa <=", value, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaIn(List<Integer> values) {
            addCriterion("alexa in", values, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaNotIn(List<Integer> values) {
            addCriterion("alexa not in", values, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaBetween(Integer value1, Integer value2) {
            addCriterion("alexa between", value1, value2, "alexa");
            return (Criteria) this;
        }

        public Criteria andAlexaNotBetween(Integer value1, Integer value2) {
            addCriterion("alexa not between", value1, value2, "alexa");
            return (Criteria) this;
        }

        public Criteria andGooglerankIsNull() {
            addCriterion("googlerank is null");
            return (Criteria) this;
        }

        public Criteria andGooglerankIsNotNull() {
            addCriterion("googlerank is not null");
            return (Criteria) this;
        }

        public Criteria andGooglerankEqualTo(Integer value) {
            addCriterion("googlerank =", value, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankNotEqualTo(Integer value) {
            addCriterion("googlerank <>", value, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankGreaterThan(Integer value) {
            addCriterion("googlerank >", value, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankGreaterThanOrEqualTo(Integer value) {
            addCriterion("googlerank >=", value, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankLessThan(Integer value) {
            addCriterion("googlerank <", value, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankLessThanOrEqualTo(Integer value) {
            addCriterion("googlerank <=", value, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankIn(List<Integer> values) {
            addCriterion("googlerank in", values, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankNotIn(List<Integer> values) {
            addCriterion("googlerank not in", values, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankBetween(Integer value1, Integer value2) {
            addCriterion("googlerank between", value1, value2, "googlerank");
            return (Criteria) this;
        }

        public Criteria andGooglerankNotBetween(Integer value1, Integer value2) {
            addCriterion("googlerank not between", value1, value2, "googlerank");
            return (Criteria) this;
        }

        public Criteria andMyrankIsNull() {
            addCriterion("myrank is null");
            return (Criteria) this;
        }

        public Criteria andMyrankIsNotNull() {
            addCriterion("myrank is not null");
            return (Criteria) this;
        }

        public Criteria andMyrankEqualTo(Integer value) {
            addCriterion("myrank =", value, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankNotEqualTo(Integer value) {
            addCriterion("myrank <>", value, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankGreaterThan(Integer value) {
            addCriterion("myrank >", value, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankGreaterThanOrEqualTo(Integer value) {
            addCriterion("myrank >=", value, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankLessThan(Integer value) {
            addCriterion("myrank <", value, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankLessThanOrEqualTo(Integer value) {
            addCriterion("myrank <=", value, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankIn(List<Integer> values) {
            addCriterion("myrank in", values, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankNotIn(List<Integer> values) {
            addCriterion("myrank not in", values, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankBetween(Integer value1, Integer value2) {
            addCriterion("myrank between", value1, value2, "myrank");
            return (Criteria) this;
        }

        public Criteria andMyrankNotBetween(Integer value1, Integer value2) {
            addCriterion("myrank not between", value1, value2, "myrank");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andCrdateIsNull() {
            addCriterion("crdate is null");
            return (Criteria) this;
        }

        public Criteria andCrdateIsNotNull() {
            addCriterion("crdate is not null");
            return (Criteria) this;
        }

        public Criteria andCrdateEqualTo(Date value) {
            addCriterion("crdate =", value, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateNotEqualTo(Date value) {
            addCriterion("crdate <>", value, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateGreaterThan(Date value) {
            addCriterion("crdate >", value, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateGreaterThanOrEqualTo(Date value) {
            addCriterion("crdate >=", value, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateLessThan(Date value) {
            addCriterion("crdate <", value, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateLessThanOrEqualTo(Date value) {
            addCriterion("crdate <=", value, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateIn(List<Date> values) {
            addCriterion("crdate in", values, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateNotIn(List<Date> values) {
            addCriterion("crdate not in", values, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateBetween(Date value1, Date value2) {
            addCriterion("crdate between", value1, value2, "crdate");
            return (Criteria) this;
        }

        public Criteria andCrdateNotBetween(Date value1, Date value2) {
            addCriterion("crdate not between", value1, value2, "crdate");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table website
     *
     * @mbggenerated do_not_delete_during_merge Sat May 14 09:01:31 CST 2016
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table website
     *
     * @mbggenerated Sat May 14 09:01:31 CST 2016
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}