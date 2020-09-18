public class Base {
    String protocol = "http";
    String hostName = "www.omdbapi.com";
//    String URIPathForBooksInformation = "/books/v1/volumes";

//    http://www.omdbapi.com/?t=blade+runner&apikey=7fa9faeb

    RestServer server = new RestServer(protocol, hostName);
}
