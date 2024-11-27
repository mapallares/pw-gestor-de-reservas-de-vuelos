import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";
import "./Button.css";

const Button = ({
  color = "accent",
  variant = "solid",
  size = "m",
  radius = "m",
  disabled = false,
  loading = false,
  width = "fit",
  icon = false,
  children,
  onClick,
}) => {
  const [button, setButton] = useState({
    color,
    variant,
    size,
    radius,
    disabled,
    loading,
    width,
    icon,
  });

  useEffect(() => {
    setButton({
      color,
      variant,
      size,
      radius,
      disabled,
      loading,
      width,
      icon,
    });
  }, [color, variant, size, radius, disabled, loading, width, icon]);

  const setLoading = (value) => {
    setButton((prevButton) => ({
      ...prevButton,
      loading: value,
    }));
  };

  const className = [
    `pw-btn-${button.color}`,
    `pw-btn-${button.variant}`,
    `pw-btn-size-${button.size}`,
    `pw-btn-radius-${button.radius}`,
    `pw-btn-width-${button.width}`,
    button.icon && "pw-btn-icon",
    button.disabled && "pw-btn-status-disabled",
    button.loading && "pw-btn-status-loading",
  ]
    .filter(Boolean)
    .join(" ");

  return (
    <button
      className={className}
      onClick={async (event) => {
        if (typeof onClick === "function") {
          setLoading(true);
          try {
            await onClick(event);
          }
          catch(error) {
            console.log(error)
          }
          setLoading(false);
        }
      }}
      disabled={button.disabled || button.loading}
    >
      {button.loading ? <span className="pw-btn-loader"></span> : children}
    </button>
  );
};

Button.propTypes = {
  color: PropTypes.oneOf([
    "accent",
    "black",
    "white",
    "gray",
    "success",
    "warning",
    "danger",
  ]),
  variant: PropTypes.oneOf(["solid", "bordered", "plain", "dimed", "stroke"]),
  size: PropTypes.oneOf(["xs", "s", "m", "l", "xl"]),
  radius: PropTypes.oneOf([
    "xs",
    "s",
    "m",
    "l",
    "auto",
    "full",
    "circle",
    "none",
  ]),
  width: PropTypes.oneOf(["full", "fit"]),
  icon: PropTypes.bool,
  children: PropTypes.node,
  onClick: PropTypes.func,
};

export default Button;
