select one.id       as one_id,
       one.name     as one_name,
       one.parentid as one_parentid,
       one.label    as one_label,
       one.orderby  as one_orderby,

       two.id       as two_id,
       two.name     as two_name,
       two.parentid as two_parentid,
       two.label    as two_label,
       two.orderby  as two_orderby
from course_category as one
         inner join course_category as two
                    on two.parentid = one.id

where one.parentid = '1'
  and one.is_show = '1'
  and two.is_show = '1'

order by one_orderby, two_orderby;


# 递归查询树形结构
with recursive t1 as (select 1 as n
                      union all
                      select n + 1
                      from t1
                      where n < 8)
select *
from t1;

# 递归查询树形结构

with recursive t1 as (select *
                      from course_category
                      where id = '1'
                      union all
                      select t2.*
                      from course_category as t2
                               inner join t1
                                          on t1.id = t2.parentid)
select *
from t1;

## 向上递归
with recursive t1 as (select *
                      from course_category
                      where id = '1-1-1'
                      union all
                      select t2.*
                      from course_category as t2
                               inner join t1
                                          on t1.parentid = t2.id)
select *
from t1;