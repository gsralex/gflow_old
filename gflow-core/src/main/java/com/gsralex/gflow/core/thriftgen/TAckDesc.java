/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.gsralex.gflow.core.thriftgen;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2018-10-25")
public class TAckDesc implements org.apache.thrift.TBase<TAckDesc, TAckDesc._Fields>, java.io.Serializable, Cloneable, Comparable<TAckDesc> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TAckDesc");

  private static final org.apache.thrift.protocol.TField JOB_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("jobId", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField ERRMSG_FIELD_DESC = new org.apache.thrift.protocol.TField("errmsg", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ACCESS_TOKEN_FIELD_DESC = new org.apache.thrift.protocol.TField("accessToken", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TAckDescStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TAckDescTupleSchemeFactory();

  public long jobId; // required
  public int code; // required
  public String errmsg; // required
  public String accessToken; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    JOB_ID((short)1, "jobId"),
    CODE((short)2, "code"),
    ERRMSG((short)3, "errmsg"),
    ACCESS_TOKEN((short)4, "accessToken");

    private static final java.util.Map<String, _Fields> byName = new java.util.HashMap<String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // JOB_ID
          return JOB_ID;
        case 2: // CODE
          return CODE;
        case 3: // ERRMSG
          return ERRMSG;
        case 4: // ACCESS_TOKEN
          return ACCESS_TOKEN;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __JOBID_ISSET_ID = 0;
  private static final int __CODE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.JOB_ID, new org.apache.thrift.meta_data.FieldMetaData("jobId", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ERRMSG, new org.apache.thrift.meta_data.FieldMetaData("errmsg", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ACCESS_TOKEN, new org.apache.thrift.meta_data.FieldMetaData("accessToken", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TAckDesc.class, metaDataMap);
  }

  public TAckDesc() {
  }

  public TAckDesc(
    long jobId,
    int code,
    String errmsg,
    String accessToken)
  {
    this();
    this.jobId = jobId;
    setJobIdIsSet(true);
    this.code = code;
    setCodeIsSet(true);
    this.errmsg = errmsg;
    this.accessToken = accessToken;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TAckDesc(TAckDesc other) {
    __isset_bitfield = other.__isset_bitfield;
    this.jobId = other.jobId;
    this.code = other.code;
    if (other.isSetErrmsg()) {
      this.errmsg = other.errmsg;
    }
    if (other.isSetAccessToken()) {
      this.accessToken = other.accessToken;
    }
  }

  public TAckDesc deepCopy() {
    return new TAckDesc(this);
  }

  @Override
  public void clear() {
    setJobIdIsSet(false);
    this.jobId = 0;
    setCodeIsSet(false);
    this.code = 0;
    this.errmsg = null;
    this.accessToken = null;
  }

  public long getJobId() {
    return this.jobId;
  }

  public TAckDesc setJobId(long jobId) {
    this.jobId = jobId;
    setJobIdIsSet(true);
    return this;
  }

  public void unsetJobId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __JOBID_ISSET_ID);
  }

  /** Returns true if field jobId is set (has been assigned a value) and false otherwise */
  public boolean isSetJobId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __JOBID_ISSET_ID);
  }

  public void setJobIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __JOBID_ISSET_ID, value);
  }

  public int getCode() {
    return this.code;
  }

  public TAckDesc setCode(int code) {
    this.code = code;
    setCodeIsSet(true);
    return this;
  }

  public void unsetCode() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  /** Returns true if field code is set (has been assigned a value) and false otherwise */
  public boolean isSetCode() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CODE_ISSET_ID);
  }

  public void setCodeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CODE_ISSET_ID, value);
  }

  public String getErrmsg() {
    return this.errmsg;
  }

  public TAckDesc setErrmsg(String errmsg) {
    this.errmsg = errmsg;
    return this;
  }

  public void unsetErrmsg() {
    this.errmsg = null;
  }

  /** Returns true if field errmsg is set (has been assigned a value) and false otherwise */
  public boolean isSetErrmsg() {
    return this.errmsg != null;
  }

  public void setErrmsgIsSet(boolean value) {
    if (!value) {
      this.errmsg = null;
    }
  }

  public String getAccessToken() {
    return this.accessToken;
  }

  public TAckDesc setAccessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  public void unsetAccessToken() {
    this.accessToken = null;
  }

  /** Returns true if field accessToken is set (has been assigned a value) and false otherwise */
  public boolean isSetAccessToken() {
    return this.accessToken != null;
  }

  public void setAccessTokenIsSet(boolean value) {
    if (!value) {
      this.accessToken = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case JOB_ID:
      if (value == null) {
        unsetJobId();
      } else {
        setJobId((Long)value);
      }
      break;

    case CODE:
      if (value == null) {
        unsetCode();
      } else {
        setCode((Integer)value);
      }
      break;

    case ERRMSG:
      if (value == null) {
        unsetErrmsg();
      } else {
        setErrmsg((String)value);
      }
      break;

    case ACCESS_TOKEN:
      if (value == null) {
        unsetAccessToken();
      } else {
        setAccessToken((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case JOB_ID:
      return getJobId();

    case CODE:
      return getCode();

    case ERRMSG:
      return getErrmsg();

    case ACCESS_TOKEN:
      return getAccessToken();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case JOB_ID:
      return isSetJobId();
    case CODE:
      return isSetCode();
    case ERRMSG:
      return isSetErrmsg();
    case ACCESS_TOKEN:
      return isSetAccessToken();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TAckDesc)
      return this.equals((TAckDesc)that);
    return false;
  }

  public boolean equals(TAckDesc that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_jobId = true;
    boolean that_present_jobId = true;
    if (this_present_jobId || that_present_jobId) {
      if (!(this_present_jobId && that_present_jobId))
        return false;
      if (this.jobId != that.jobId)
        return false;
    }

    boolean this_present_code = true;
    boolean that_present_code = true;
    if (this_present_code || that_present_code) {
      if (!(this_present_code && that_present_code))
        return false;
      if (this.code != that.code)
        return false;
    }

    boolean this_present_errmsg = true && this.isSetErrmsg();
    boolean that_present_errmsg = true && that.isSetErrmsg();
    if (this_present_errmsg || that_present_errmsg) {
      if (!(this_present_errmsg && that_present_errmsg))
        return false;
      if (!this.errmsg.equals(that.errmsg))
        return false;
    }

    boolean this_present_accessToken = true && this.isSetAccessToken();
    boolean that_present_accessToken = true && that.isSetAccessToken();
    if (this_present_accessToken || that_present_accessToken) {
      if (!(this_present_accessToken && that_present_accessToken))
        return false;
      if (!this.accessToken.equals(that.accessToken))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(jobId);

    hashCode = hashCode * 8191 + code;

    hashCode = hashCode * 8191 + ((isSetErrmsg()) ? 131071 : 524287);
    if (isSetErrmsg())
      hashCode = hashCode * 8191 + errmsg.hashCode();

    hashCode = hashCode * 8191 + ((isSetAccessToken()) ? 131071 : 524287);
    if (isSetAccessToken())
      hashCode = hashCode * 8191 + accessToken.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(TAckDesc other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetJobId()).compareTo(other.isSetJobId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetJobId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobId, other.jobId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCode()).compareTo(other.isSetCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, other.code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetErrmsg()).compareTo(other.isSetErrmsg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrmsg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errmsg, other.errmsg);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetAccessToken()).compareTo(other.isSetAccessToken());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetAccessToken()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.accessToken, other.accessToken);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("TAckDesc(");
    boolean first = true;

    sb.append("jobId:");
    sb.append(this.jobId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("code:");
    sb.append(this.code);
    first = false;
    if (!first) sb.append(", ");
    sb.append("errmsg:");
    if (this.errmsg == null) {
      sb.append("null");
    } else {
      sb.append(this.errmsg);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("accessToken:");
    if (this.accessToken == null) {
      sb.append("null");
    } else {
      sb.append(this.accessToken);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TAckDescStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TAckDescStandardScheme getScheme() {
      return new TAckDescStandardScheme();
    }
  }

  private static class TAckDescStandardScheme extends org.apache.thrift.scheme.StandardScheme<TAckDesc> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TAckDesc struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // JOB_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.jobId = iprot.readI64();
              struct.setJobIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.code = iprot.readI32();
              struct.setCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ERRMSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.errmsg = iprot.readString();
              struct.setErrmsgIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ACCESS_TOKEN
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.accessToken = iprot.readString();
              struct.setAccessTokenIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TAckDesc struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(JOB_ID_FIELD_DESC);
      oprot.writeI64(struct.jobId);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CODE_FIELD_DESC);
      oprot.writeI32(struct.code);
      oprot.writeFieldEnd();
      if (struct.errmsg != null) {
        oprot.writeFieldBegin(ERRMSG_FIELD_DESC);
        oprot.writeString(struct.errmsg);
        oprot.writeFieldEnd();
      }
      if (struct.accessToken != null) {
        oprot.writeFieldBegin(ACCESS_TOKEN_FIELD_DESC);
        oprot.writeString(struct.accessToken);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TAckDescTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TAckDescTupleScheme getScheme() {
      return new TAckDescTupleScheme();
    }
  }

  private static class TAckDescTupleScheme extends org.apache.thrift.scheme.TupleScheme<TAckDesc> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TAckDesc struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetJobId()) {
        optionals.set(0);
      }
      if (struct.isSetCode()) {
        optionals.set(1);
      }
      if (struct.isSetErrmsg()) {
        optionals.set(2);
      }
      if (struct.isSetAccessToken()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetJobId()) {
        oprot.writeI64(struct.jobId);
      }
      if (struct.isSetCode()) {
        oprot.writeI32(struct.code);
      }
      if (struct.isSetErrmsg()) {
        oprot.writeString(struct.errmsg);
      }
      if (struct.isSetAccessToken()) {
        oprot.writeString(struct.accessToken);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TAckDesc struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.jobId = iprot.readI64();
        struct.setJobIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.code = iprot.readI32();
        struct.setCodeIsSet(true);
      }
      if (incoming.get(2)) {
        struct.errmsg = iprot.readString();
        struct.setErrmsgIsSet(true);
      }
      if (incoming.get(3)) {
        struct.accessToken = iprot.readString();
        struct.setAccessTokenIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

