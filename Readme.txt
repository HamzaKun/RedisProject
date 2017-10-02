To run the project:
  I made two main functions, one is in the Publisher class that runs a publisher, the second one
  is on the Subscriber class that lunches the subscriber.

  In the next section, I'll describe my implementation.

================================================================================================================================

In this document I'll describe the work that I've done one the redis lab.
For working on this lab, I chose to work with Java as a programming language, with the library Jedis.
To work with Jedis, we need to first instantiate the object Jedis(), this object will allow us to interact with the redis server.
In my implementation, I chose to split my code into two packages :
  The business package: in which I did put the Book object, that corresponds to the object to be persisted into the database.
    The Book object has these attributes:
      -ISBN: String
      -Title: String
      -Description: String
      -Authors: String, that represents the list of authors.
The root package : In this one, I have two classes, the first represents the Publisher, and the second represents the Subscriber.
    Publisher: In this function, I do insert the books and do publish their data to the appropriate channels.
        -The publisher's main function has a method publishBook(Book book), this function gets an object Book, gets the description
        of the book and then splits this description to get all the keywords.Afterwards it removes the stop words (as, if, are, but, ...) from it,
        to publish the new book to all the channels.
    Receiver: This function asks the user for the channel that he wants to subscribe to, and then updates him with the new books
    related to the subscribed channel.
        -The subscriber also has a main function, that uses the Jedis object, and the JedisPubSub object, that allows us to subscribe
        to a given channel, and also specifies some custom behaviors when we subscribe to a channel, a message has been received and many other events.

The previous two objects have a main function each, that connects to the Redis server, and communicates with the user.
===================================================================================================================================

Author : KASRY Hamza
University Paris-Saclay, Télécom ParisTech

The code can be found in : https://github.com/HamzaKun/RedisProject
