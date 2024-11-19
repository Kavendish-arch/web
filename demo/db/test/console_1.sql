with recursive t1 as (select 1 as n
                      union all
                      select n + 1
                      from t1
                      where n < 10)
select *
from t1;

with recursive t1 as (select *
                      from course_category
                      where id = '1'
                      union all
                      select t2.*
                      from course_category t2
                               inner join t1 on t1.id = t2.parentid)
select *
from t1
order by t1.id;


select id, name, label, parentid, is_show, orderby, is_leaf

from course_category

where is_show = '1'

order by orderby;