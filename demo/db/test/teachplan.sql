select one.id            one_id,
       one.pname         one_pname,
       one.parentid      one_parentid,
       one.grade         one_grade,
       one.media_type    one_mediaType,
       one.start_time    one_startTime,
       one.end_time      one_endTime,
       one.orderby       one_orderby,
       one.course_id     one_courseId,
       one.course_pub_id one_coursePubId,
       two.id            two_id,
       two.pname         two_pname,
       two.parentid      two_parentid,
       two.grade         two_grade,
       two.media_type    two_mediaType,
       two.start_time    two_startTime,
       two.end_time      two_endTime,
       two.orderby       two_orderby,
       two.course_id     two_courseId,
       two.course_pub_id two_coursePubId,
       m1.media_fileName mediaFilename,
       m1.id             teachplanMeidaId,
       m1.media_id       mediaId

from teachplan one
         INNER JOIN teachplan two on one.id = two.parentid
         LEFT JOIN teachplan_media m1 on m1.teachplan_id = two.id
where one.parentid = 0
  and one.course_id = 1
order by one.orderby,
         two.orderby;

select one.id            one_id,
       one.pname         one_pname,
       one.parentid      one_parentid,
       one.grade         one_grade,
       one.media_type    one_mediaType,
       one.start_time    one_startTime,
       one.end_time      one_endTime,
       one.orderby       one_orderby,
       one.course_id     one_courseId,
       one.course_pub_id one_coursePubId,
       two.id            two_id,
       two.pname         two_pname,
       two.parentid      two_parentid,
       two.grade         two_grade,
       two.media_type    two_mediaType,
       two.start_time    two_startTime,
       two.end_time      two_endTime,
       two.orderby       two_orderby,
       two.course_id     two_courseId,
       two.course_pub_id two_coursePubId,
       m1.media_fileName mediaFilename,
       m1.id             teachplanMeidaId,
       m1.media_id       mediaId

from teachplan one
         LEFT JOIN teachplan two on one.id = two.parentid
         LEFT JOIN teachplan_media m1 on m1.teachplan_id = two.id
where one.parentid = 0
  and one.course_id = 1
order by one.orderby,
         two.orderby;