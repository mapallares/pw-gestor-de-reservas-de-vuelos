import React, { useState } from "react"

function Table({ dataObjects, id = "table", attrs = [], include = false, translate = {} }) {
  const [search, setSearch] = useState("")
  const [sortConfig, setSortConfig] = useState({ key: null, direction: "asc" })

  let data = []

  if (attrs.length > 0) {
    dataObjects.forEach((dataObject) => {
      const obj = {}
      Object.keys(dataObject).forEach((attr) => {
        if (attrs.includes(attr) === include) {
          obj[attr] = dataObject[attr]
        }
      })
      data.push(obj)
    })
  } else {
    data = dataObjects
  }

  if (!Array.isArray(data) || data.length === 0) {
    return <p style={{ padding: "20px" }}>No hay datos disponibles</p>
  }

  const headers = Object.keys(data[0])

  const handleSearch = (e) => {
    setSearch(e.target.value.toLowerCase())
  }

  const handleSort = (key) => {
    setSortConfig((prev) => ({
      key,
      direction: prev.key === key && prev.direction === "asc" ? "desc" : "asc",
    }))
  }

  const filteredData = data.filter((row) =>
    headers.some((header) =>
      row[header]?.toString().toLowerCase().includes(search)
    )
  )

  const sortedData = [...filteredData].sort((a, b) => {
    if (!sortConfig.key) return 0
    const aValue = a[sortConfig.key]
    const bValue = b[sortConfig.key]

    if (aValue < bValue) return sortConfig.direction === "asc" ? -1 : 1
    if (aValue > bValue) return sortConfig.direction === "asc" ? 1 : -1
    return 0
  })

  return (
    <div className="pw-layout-table-container">
      <div className="pw-layout-table-search-container">
          <div className="pw-layout-table-search-content">
          <span className="material-symbols-outlined">search</span>
          <input
            type="text"
            placeholder="Buscar..."
            value={search}
            onChange={handleSearch}
            className="pw-layout-table-search"
          />
        </div>
      </div>
      <div className="pw-layout-table-content">
      <table id={id} className="pw-layout-table">
        <thead>
          <tr>
            {headers.map((header) => (
              <th
                key={header}
                onClick={() => handleSort(header)}
                style={{
                  cursor: "pointer",
                  color: sortConfig.key === header ? "var(--pw-color-accent)" : "",
                }}
              >
                {translate[header] || header}{" "}
                {sortConfig.key === header
                  ? sortConfig.direction === "asc"
                    ? "↑"
                    : "↓"
                  : ""}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {sortedData.length > 0 ? sortedData.map((row, index) => (
            <tr
              className="pw-layout-table-row"
              id={`${id}_${dataObjects[index].id}`}
              key={`${id}_${dataObjects[index].id}`}
            >
              {headers.map((header) => (
                <td key={header}>{row[header] ?? ""}</td>
              ))}
            </tr>
          )) : <tr><td colSpan={headers.length} className="pw-layout-table-empty">Sin resultados</td></tr>}
        </tbody>
      </table>
      </div>
      <p className="pw-layout-table-total"><strong>Total:</strong> {sortedData.length}</p>
    </div>
  )
}

export default Table
