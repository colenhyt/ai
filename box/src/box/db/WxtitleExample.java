package box.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class WxtitleExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public WxtitleExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
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
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andWxhaoIsNull() {
            addCriterion("wxhao is null");
            return (Criteria) this;
        }

        public Criteria andWxhaoIsNotNull() {
            addCriterion("wxhao is not null");
            return (Criteria) this;
        }

        public Criteria andWxhaoEqualTo(String value) {
            addCriterion("wxhao =", value, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoNotEqualTo(String value) {
            addCriterion("wxhao <>", value, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoGreaterThan(String value) {
            addCriterion("wxhao >", value, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoGreaterThanOrEqualTo(String value) {
            addCriterion("wxhao >=", value, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoLessThan(String value) {
            addCriterion("wxhao <", value, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoLessThanOrEqualTo(String value) {
            addCriterion("wxhao <=", value, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoLike(String value) {
            addCriterion("wxhao like", value, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoNotLike(String value) {
            addCriterion("wxhao not like", value, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoIn(List<String> values) {
            addCriterion("wxhao in", values, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoNotIn(List<String> values) {
            addCriterion("wxhao not in", values, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoBetween(String value1, String value2) {
            addCriterion("wxhao between", value1, value2, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxhaoNotBetween(String value1, String value2) {
            addCriterion("wxhao not between", value1, value2, "wxhao");
            return (Criteria) this;
        }

        public Criteria andWxnameIsNull() {
            addCriterion("wxname is null");
            return (Criteria) this;
        }

        public Criteria andWxnameIsNotNull() {
            addCriterion("wxname is not null");
            return (Criteria) this;
        }

        public Criteria andWxnameEqualTo(String value) {
            addCriterion("wxname =", value, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameNotEqualTo(String value) {
            addCriterion("wxname <>", value, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameGreaterThan(String value) {
            addCriterion("wxname >", value, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameGreaterThanOrEqualTo(String value) {
            addCriterion("wxname >=", value, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameLessThan(String value) {
            addCriterion("wxname <", value, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameLessThanOrEqualTo(String value) {
            addCriterion("wxname <=", value, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameLike(String value) {
            addCriterion("wxname like", value, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameNotLike(String value) {
            addCriterion("wxname not like", value, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameIn(List<String> values) {
            addCriterion("wxname in", values, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameNotIn(List<String> values) {
            addCriterion("wxname not in", values, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameBetween(String value1, String value2) {
            addCriterion("wxname between", value1, value2, "wxname");
            return (Criteria) this;
        }

        public Criteria andWxnameNotBetween(String value1, String value2) {
            addCriterion("wxname not between", value1, value2, "wxname");
            return (Criteria) this;
        }

        public Criteria andOpenidIsNull() {
            addCriterion("openid is null");
            return (Criteria) this;
        }

        public Criteria andOpenidIsNotNull() {
            addCriterion("openid is not null");
            return (Criteria) this;
        }

        public Criteria andOpenidEqualTo(String value) {
            addCriterion("openid =", value, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidNotEqualTo(String value) {
            addCriterion("openid <>", value, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidGreaterThan(String value) {
            addCriterion("openid >", value, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidGreaterThanOrEqualTo(String value) {
            addCriterion("openid >=", value, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidLessThan(String value) {
            addCriterion("openid <", value, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidLessThanOrEqualTo(String value) {
            addCriterion("openid <=", value, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidLike(String value) {
            addCriterion("openid like", value, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidNotLike(String value) {
            addCriterion("openid not like", value, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidIn(List<String> values) {
            addCriterion("openid in", values, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidNotIn(List<String> values) {
            addCriterion("openid not in", values, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidBetween(String value1, String value2) {
            addCriterion("openid between", value1, value2, "openid");
            return (Criteria) this;
        }

        public Criteria andOpenidNotBetween(String value1, String value2) {
            addCriterion("openid not between", value1, value2, "openid");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleurlIsNull() {
            addCriterion("titleurl is null");
            return (Criteria) this;
        }

        public Criteria andTitleurlIsNotNull() {
            addCriterion("titleurl is not null");
            return (Criteria) this;
        }

        public Criteria andTitleurlEqualTo(String value) {
            addCriterion("titleurl =", value, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlNotEqualTo(String value) {
            addCriterion("titleurl <>", value, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlGreaterThan(String value) {
            addCriterion("titleurl >", value, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlGreaterThanOrEqualTo(String value) {
            addCriterion("titleurl >=", value, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlLessThan(String value) {
            addCriterion("titleurl <", value, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlLessThanOrEqualTo(String value) {
            addCriterion("titleurl <=", value, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlLike(String value) {
            addCriterion("titleurl like", value, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlNotLike(String value) {
            addCriterion("titleurl not like", value, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlIn(List<String> values) {
            addCriterion("titleurl in", values, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlNotIn(List<String> values) {
            addCriterion("titleurl not in", values, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlBetween(String value1, String value2) {
            addCriterion("titleurl between", value1, value2, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitleurlNotBetween(String value1, String value2) {
            addCriterion("titleurl not between", value1, value2, "titleurl");
            return (Criteria) this;
        }

        public Criteria andTitlekeyIsNull() {
            addCriterion("titlekey is null");
            return (Criteria) this;
        }

        public Criteria andTitlekeyIsNotNull() {
            addCriterion("titlekey is not null");
            return (Criteria) this;
        }

        public Criteria andTitlekeyEqualTo(String value) {
            addCriterion("titlekey =", value, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyNotEqualTo(String value) {
            addCriterion("titlekey <>", value, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyGreaterThan(String value) {
            addCriterion("titlekey >", value, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyGreaterThanOrEqualTo(String value) {
            addCriterion("titlekey >=", value, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyLessThan(String value) {
            addCriterion("titlekey <", value, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyLessThanOrEqualTo(String value) {
            addCriterion("titlekey <=", value, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyLike(String value) {
            addCriterion("titlekey like", value, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyNotLike(String value) {
            addCriterion("titlekey not like", value, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyIn(List<String> values) {
            addCriterion("titlekey in", values, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyNotIn(List<String> values) {
            addCriterion("titlekey not in", values, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyBetween(String value1, String value2) {
            addCriterion("titlekey between", value1, value2, "titlekey");
            return (Criteria) this;
        }

        public Criteria andTitlekeyNotBetween(String value1, String value2) {
            addCriterion("titlekey not between", value1, value2, "titlekey");
            return (Criteria) this;
        }

        public Criteria andViewcountIsNull() {
            addCriterion("viewcount is null");
            return (Criteria) this;
        }

        public Criteria andViewcountIsNotNull() {
            addCriterion("viewcount is not null");
            return (Criteria) this;
        }

        public Criteria andViewcountEqualTo(Integer value) {
            addCriterion("viewcount =", value, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountNotEqualTo(Integer value) {
            addCriterion("viewcount <>", value, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountGreaterThan(Integer value) {
            addCriterion("viewcount >", value, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountGreaterThanOrEqualTo(Integer value) {
            addCriterion("viewcount >=", value, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountLessThan(Integer value) {
            addCriterion("viewcount <", value, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountLessThanOrEqualTo(Integer value) {
            addCriterion("viewcount <=", value, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountIn(List<Integer> values) {
            addCriterion("viewcount in", values, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountNotIn(List<Integer> values) {
            addCriterion("viewcount not in", values, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountBetween(Integer value1, Integer value2) {
            addCriterion("viewcount between", value1, value2, "viewcount");
            return (Criteria) this;
        }

        public Criteria andViewcountNotBetween(Integer value1, Integer value2) {
            addCriterion("viewcount not between", value1, value2, "viewcount");
            return (Criteria) this;
        }

        public Criteria andZancountIsNull() {
            addCriterion("zancount is null");
            return (Criteria) this;
        }

        public Criteria andZancountIsNotNull() {
            addCriterion("zancount is not null");
            return (Criteria) this;
        }

        public Criteria andZancountEqualTo(Integer value) {
            addCriterion("zancount =", value, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountNotEqualTo(Integer value) {
            addCriterion("zancount <>", value, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountGreaterThan(Integer value) {
            addCriterion("zancount >", value, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountGreaterThanOrEqualTo(Integer value) {
            addCriterion("zancount >=", value, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountLessThan(Integer value) {
            addCriterion("zancount <", value, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountLessThanOrEqualTo(Integer value) {
            addCriterion("zancount <=", value, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountIn(List<Integer> values) {
            addCriterion("zancount in", values, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountNotIn(List<Integer> values) {
            addCriterion("zancount not in", values, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountBetween(Integer value1, Integer value2) {
            addCriterion("zancount between", value1, value2, "zancount");
            return (Criteria) this;
        }

        public Criteria andZancountNotBetween(Integer value1, Integer value2) {
            addCriterion("zancount not between", value1, value2, "zancount");
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

        public Criteria andUdateIsNull() {
            addCriterion("udate is null");
            return (Criteria) this;
        }

        public Criteria andUdateIsNotNull() {
            addCriterion("udate is not null");
            return (Criteria) this;
        }

        public Criteria andUdateEqualTo(Date value) {
            addCriterion("udate =", value, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateNotEqualTo(Date value) {
            addCriterion("udate <>", value, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateGreaterThan(Date value) {
            addCriterion("udate >", value, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateGreaterThanOrEqualTo(Date value) {
            addCriterion("udate >=", value, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateLessThan(Date value) {
            addCriterion("udate <", value, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateLessThanOrEqualTo(Date value) {
            addCriterion("udate <=", value, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateIn(List<Date> values) {
            addCriterion("udate in", values, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateNotIn(List<Date> values) {
            addCriterion("udate not in", values, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateBetween(Date value1, Date value2) {
            addCriterion("udate between", value1, value2, "udate");
            return (Criteria) this;
        }

        public Criteria andUdateNotBetween(Date value1, Date value2) {
            addCriterion("udate not between", value1, value2, "udate");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table wxtitle
     *
     * @mbggenerated do_not_delete_during_merge Fri Mar 27 10:01:03 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table wxtitle
     *
     * @mbggenerated Fri Mar 27 10:01:03 CST 2015
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