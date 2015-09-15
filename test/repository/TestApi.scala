package repository

import conf.connection.DataConnection

/**
 * Created by hashcode on 2015/09/15.
 */
abstract class TestApi {
  implicit val keyspace = DataConnection.keySpace
  implicit val session = DataConnection.session

}
