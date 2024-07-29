
package dao;

import dao.custom.impl.BookDaoImpl;
import dao.custom.impl.BorrowDaoImpl;
import dao.custom.impl.BorrowDetailsDaoImpl;
import dao.custom.impl.CollectDaoImpl;
import dao.custom.impl.FineDaoImpl;
import dao.custom.impl.GenreDaoImpl;
import dao.custom.impl.LibrarianDaoImpl;
import dao.custom.impl.MemberDaoImpl;

public class DaoFactory {

    private static DaoFactory daoFactory;

    private DaoFactory() {
    }

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public SuperDAO getDao(DaoTypes type) {
        switch (type) {
            case LIBRARIAN:
                return new LibrarianDaoImpl();
            case MEMBER:
                return new MemberDaoImpl();
            case BOOK:
                return new BookDaoImpl();
            case BORROW:
                return new BorrowDaoImpl();
            case BORROW_DETAILS:
                return new BorrowDetailsDaoImpl();
            case FINE:
                return new FineDaoImpl();
            case COLLECT:
                return new CollectDaoImpl();
            case GENRE:
                return new GenreDaoImpl();
            default:
                return null;
        }
    }

    public enum DaoTypes {
        LIBRARIAN, MEMBER, BOOK, BORROW, BORROW_DETAILS, FINE, COLLECT, GENRE
    }
}
