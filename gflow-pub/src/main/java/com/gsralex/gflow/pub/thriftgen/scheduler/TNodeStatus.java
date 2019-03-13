/**
 * Autogenerated by Thrift Compiler (0.11.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.gsralex.gflow.pub.thriftgen.scheduler;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.11.0)", date = "2019-03-13")
public class TNodeStatus implements org.apache.thrift.TBase<TNodeStatus, TNodeStatus._Fields>, java.io.Serializable, Cloneable, Comparable<TNodeStatus> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TNodeStatus");

  private static final org.apache.thrift.protocol.TField IP_FIELD_DESC = new org.apache.thrift.protocol.TField("ip", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField PORT_FIELD_DESC = new org.apache.thrift.protocol.TField("port", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField TAG_FIELD_DESC = new org.apache.thrift.protocol.TField("tag", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField ONLINE_FIELD_DESC = new org.apache.thrift.protocol.TField("online", org.apache.thrift.protocol.TType.BOOL, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new TNodeStatusStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new TNodeStatusTupleSchemeFactory();

  public java.lang.String ip; // required
  public int port; // required
  public java.lang.String tag; // required
  public boolean online; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IP((short)1, "ip"),
    PORT((short)2, "port"),
    TAG((short)3, "tag"),
    ONLINE((short)4, "online");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

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
        case 1: // IP
          return IP;
        case 2: // PORT
          return PORT;
        case 3: // TAG
          return TAG;
        case 4: // ONLINE
          return ONLINE;
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
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __PORT_ISSET_ID = 0;
  private static final int __ONLINE_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IP, new org.apache.thrift.meta_data.FieldMetaData("ip", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PORT, new org.apache.thrift.meta_data.FieldMetaData("port", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TAG, new org.apache.thrift.meta_data.FieldMetaData("tag", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ONLINE, new org.apache.thrift.meta_data.FieldMetaData("online", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TNodeStatus.class, metaDataMap);
  }

  public TNodeStatus() {
  }

  public TNodeStatus(
    java.lang.String ip,
    int port,
    java.lang.String tag,
    boolean online)
  {
    this();
    this.ip = ip;
    this.port = port;
    setPortIsSet(true);
    this.tag = tag;
    this.online = online;
    setOnlineIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TNodeStatus(TNodeStatus other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetIp()) {
      this.ip = other.ip;
    }
    this.port = other.port;
    if (other.isSetTag()) {
      this.tag = other.tag;
    }
    this.online = other.online;
  }

  public TNodeStatus deepCopy() {
    return new TNodeStatus(this);
  }

  @Override
  public void clear() {
    this.ip = null;
    setPortIsSet(false);
    this.port = 0;
    this.tag = null;
    setOnlineIsSet(false);
    this.online = false;
  }

  public java.lang.String getIp() {
    return this.ip;
  }

  public TNodeStatus setIp(java.lang.String ip) {
    this.ip = ip;
    return this;
  }

  public void unsetIp() {
    this.ip = null;
  }

  /** Returns true if field ip is set (has been assigned a value) and false otherwise */
  public boolean isSetIp() {
    return this.ip != null;
  }

  public void setIpIsSet(boolean value) {
    if (!value) {
      this.ip = null;
    }
  }

  public int getPort() {
    return this.port;
  }

  public TNodeStatus setPort(int port) {
    this.port = port;
    setPortIsSet(true);
    return this;
  }

  public void unsetPort() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __PORT_ISSET_ID);
  }

  /** Returns true if field port is set (has been assigned a value) and false otherwise */
  public boolean isSetPort() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __PORT_ISSET_ID);
  }

  public void setPortIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __PORT_ISSET_ID, value);
  }

  public java.lang.String getTag() {
    return this.tag;
  }

  public TNodeStatus setTag(java.lang.String tag) {
    this.tag = tag;
    return this;
  }

  public void unsetTag() {
    this.tag = null;
  }

  /** Returns true if field tag is set (has been assigned a value) and false otherwise */
  public boolean isSetTag() {
    return this.tag != null;
  }

  public void setTagIsSet(boolean value) {
    if (!value) {
      this.tag = null;
    }
  }

  public boolean isOnline() {
    return this.online;
  }

  public TNodeStatus setOnline(boolean online) {
    this.online = online;
    setOnlineIsSet(true);
    return this;
  }

  public void unsetOnline() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ONLINE_ISSET_ID);
  }

  /** Returns true if field online is set (has been assigned a value) and false otherwise */
  public boolean isSetOnline() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ONLINE_ISSET_ID);
  }

  public void setOnlineIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ONLINE_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case IP:
      if (value == null) {
        unsetIp();
      } else {
        setIp((java.lang.String)value);
      }
      break;

    case PORT:
      if (value == null) {
        unsetPort();
      } else {
        setPort((java.lang.Integer)value);
      }
      break;

    case TAG:
      if (value == null) {
        unsetTag();
      } else {
        setTag((java.lang.String)value);
      }
      break;

    case ONLINE:
      if (value == null) {
        unsetOnline();
      } else {
        setOnline((java.lang.Boolean)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case IP:
      return getIp();

    case PORT:
      return getPort();

    case TAG:
      return getTag();

    case ONLINE:
      return isOnline();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case IP:
      return isSetIp();
    case PORT:
      return isSetPort();
    case TAG:
      return isSetTag();
    case ONLINE:
      return isSetOnline();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof TNodeStatus)
      return this.equals((TNodeStatus)that);
    return false;
  }

  public boolean equals(TNodeStatus that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_ip = true && this.isSetIp();
    boolean that_present_ip = true && that.isSetIp();
    if (this_present_ip || that_present_ip) {
      if (!(this_present_ip && that_present_ip))
        return false;
      if (!this.ip.equals(that.ip))
        return false;
    }

    boolean this_present_port = true;
    boolean that_present_port = true;
    if (this_present_port || that_present_port) {
      if (!(this_present_port && that_present_port))
        return false;
      if (this.port != that.port)
        return false;
    }

    boolean this_present_tag = true && this.isSetTag();
    boolean that_present_tag = true && that.isSetTag();
    if (this_present_tag || that_present_tag) {
      if (!(this_present_tag && that_present_tag))
        return false;
      if (!this.tag.equals(that.tag))
        return false;
    }

    boolean this_present_online = true;
    boolean that_present_online = true;
    if (this_present_online || that_present_online) {
      if (!(this_present_online && that_present_online))
        return false;
      if (this.online != that.online)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetIp()) ? 131071 : 524287);
    if (isSetIp())
      hashCode = hashCode * 8191 + ip.hashCode();

    hashCode = hashCode * 8191 + port;

    hashCode = hashCode * 8191 + ((isSetTag()) ? 131071 : 524287);
    if (isSetTag())
      hashCode = hashCode * 8191 + tag.hashCode();

    hashCode = hashCode * 8191 + ((online) ? 131071 : 524287);

    return hashCode;
  }

  @Override
  public int compareTo(TNodeStatus other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetIp()).compareTo(other.isSetIp());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIp()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ip, other.ip);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetPort()).compareTo(other.isSetPort());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPort()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.port, other.port);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTag()).compareTo(other.isSetTag());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTag()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tag, other.tag);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetOnline()).compareTo(other.isSetOnline());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOnline()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.online, other.online);
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
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("TNodeStatus(");
    boolean first = true;

    sb.append("ip:");
    if (this.ip == null) {
      sb.append("null");
    } else {
      sb.append(this.ip);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("port:");
    sb.append(this.port);
    first = false;
    if (!first) sb.append(", ");
    sb.append("tag:");
    if (this.tag == null) {
      sb.append("null");
    } else {
      sb.append(this.tag);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("online:");
    sb.append(this.online);
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

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class TNodeStatusStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TNodeStatusStandardScheme getScheme() {
      return new TNodeStatusStandardScheme();
    }
  }

  private static class TNodeStatusStandardScheme extends org.apache.thrift.scheme.StandardScheme<TNodeStatus> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TNodeStatus struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IP
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ip = iprot.readString();
              struct.setIpIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // PORT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.port = iprot.readI32();
              struct.setPortIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TAG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tag = iprot.readString();
              struct.setTagIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ONLINE
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.online = iprot.readBool();
              struct.setOnlineIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, TNodeStatus struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.ip != null) {
        oprot.writeFieldBegin(IP_FIELD_DESC);
        oprot.writeString(struct.ip);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(PORT_FIELD_DESC);
      oprot.writeI32(struct.port);
      oprot.writeFieldEnd();
      if (struct.tag != null) {
        oprot.writeFieldBegin(TAG_FIELD_DESC);
        oprot.writeString(struct.tag);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ONLINE_FIELD_DESC);
      oprot.writeBool(struct.online);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TNodeStatusTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public TNodeStatusTupleScheme getScheme() {
      return new TNodeStatusTupleScheme();
    }
  }

  private static class TNodeStatusTupleScheme extends org.apache.thrift.scheme.TupleScheme<TNodeStatus> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TNodeStatus struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetIp()) {
        optionals.set(0);
      }
      if (struct.isSetPort()) {
        optionals.set(1);
      }
      if (struct.isSetTag()) {
        optionals.set(2);
      }
      if (struct.isSetOnline()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetIp()) {
        oprot.writeString(struct.ip);
      }
      if (struct.isSetPort()) {
        oprot.writeI32(struct.port);
      }
      if (struct.isSetTag()) {
        oprot.writeString(struct.tag);
      }
      if (struct.isSetOnline()) {
        oprot.writeBool(struct.online);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TNodeStatus struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      java.util.BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        struct.ip = iprot.readString();
        struct.setIpIsSet(true);
      }
      if (incoming.get(1)) {
        struct.port = iprot.readI32();
        struct.setPortIsSet(true);
      }
      if (incoming.get(2)) {
        struct.tag = iprot.readString();
        struct.setTagIsSet(true);
      }
      if (incoming.get(3)) {
        struct.online = iprot.readBool();
        struct.setOnlineIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
