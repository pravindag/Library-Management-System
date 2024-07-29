
package service;

import service.custom.impl.BookServiceImpl;
import service.custom.impl.BorrowServiceImpl;
import service.custom.impl.CollectServiceImpl;
import service.custom.impl.FineServiceImpl;
import service.custom.impl.GenreServiceImpl;
import service.custom.impl.LibrarianServiceImpl;
import service.custom.impl.MemberServiceImpl;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    private ServiceFactory() {
    }
    
    public static ServiceFactory getInstance(){
        if(serviceFactory == null){
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }
    
    public SuperService getService(ServiceType type){
        switch (type) {
            case LIBRARIAN:
                return new LibrarianServiceImpl();
            case MEMBER:
                return new MemberServiceImpl();
            case BOOK:
                return new BookServiceImpl();
            case BORROW:
                return new BorrowServiceImpl();
            case FINE:
                return new FineServiceImpl();
            case COLLECT:
                return new CollectServiceImpl();
            case GENRE:
                return new GenreServiceImpl();
            default:
                return null;
        }
    }
    
     public enum ServiceType{
        LIBRARIAN, MEMBER, BOOK, BORROW, FINE, COLLECT, GENRE
    }
}
