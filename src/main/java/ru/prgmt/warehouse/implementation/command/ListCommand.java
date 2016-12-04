package ru.prgmt.warehouse.implementation.command;


import ru.prgmt.warehouse.application.Page;
import ru.prgmt.warehouse.application.Paging;
import ru.prgmt.warehouse.application.result.CommandResult;
import ru.prgmt.warehouse.application.result.PagedResult;
import ru.prgmt.warehouse.implementation.IRepository;
import ru.prgmt.warehouse.implementation.displayResult.DisplayResult;
import ru.prgmt.warehouse.implementation.displayResult.DisplayResultComparator;
import ru.prgmt.warehouse.implementation.record.Record;

import java.lang.Math;
import java.util.*;

public class ListCommand implements ICommand {
    private final IRepository repository;
    public ListCommand(IRepository repository) {
        this.repository = repository;
    }
    @Override
    public String name() {
        return "list";
    }

    @Override
    public String description() {
        return "список инвертаря";
    }

    @Override
    public CommandResult executeCommand(Map<String, String> parameters, Paging paging) {
        String sortProperty  = "";
        boolean isSortAscending = true;
        if (paging != null) {
            sortProperty = paging.getSortProperty();
            isSortAscending = paging.isSortAscending();
        }

        DisplayResultComparator comparator = new DisplayResultComparator(sortProperty, isSortAscending);
        Collection<Record> allRecords = repository.getAllRecords();
        List<DisplayResult> allDisplayResult = new ArrayList<>();
        for (Record r : allRecords) {
            allDisplayResult.add(r.displayResult());
        }
        if (paging != null) {
            Collections.sort(allDisplayResult, comparator);
            int startIdx = paging.getPageNumber() * paging.getPageSize();
            int endIdx = startIdx + paging.getPageSize();
            if (startIdx < allDisplayResult.size()) {
                endIdx = Math.min(endIdx, allDisplayResult.size());
                List<DisplayResult> displayResults = allDisplayResult.subList(startIdx, endIdx);
                Page<DisplayResult> page = new Page<>(displayResults, paging, displayResults.size());
                return new PagedResult(page);
            }
            return new PagedResult(new Page<>(new ArrayList<>(), paging, 0));
        }
        Page<DisplayResult> page = new Page<>(allDisplayResult, null, allDisplayResult.size());
        return new PagedResult(page);

    }
}
