package com.iesvdc.dam.acceso.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * El modelo que almacena el libro o lista de tablas.
 */
public class WorkbookModel {
    private final List<TableModel> tables = new ArrayList<TableModel>();

    public WorkbookModel() {
    }

    public List<TableModel> getTables() {
        return this.tables;
    }

    public boolean addTable(TableModel table) {
        return tables.add(table);

    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof WorkbookModel)) {
            return false;
        }
        WorkbookModel workbookModel = (WorkbookModel) o;
        return Objects.equals(tables, workbookModel.tables);
    }

    @Override
    public String toString() {
        return "{" +
                " tables='" + getTables() + "'" +
                "}";
    }

}
